package com.example.AnomalyDetection.AnomalyDetector;


import com.example.AnomalyDetection.CommonFunctions;
import com.example.AnomalyDetection.Constants;
import com.example.AnomalyDetection.Filter.Filters;
import com.example.AnomalyDetection.GetData.ReadExcel;
import com.example.AnomalyDetection.ML.Clustering;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;


public class AnomalyDetection {
    final static String[] regex = {
            "ESBUlkRequestFailureException",
            "ClassNotFoundException",
            "UncategorizedExecutionException",
            "SprinklrRuntimeException",
            "IllegalArguementException",
            "GoogleJsonResponseException",
            "LinkedInRecoverableException",
            "FacebookAPIException",
            "java.lang.RuntimeException",
            "java.lang.NullPointerException",
            "java.lang.ClassCastException",
            "Exception while consuming provider recording for conversation",
            "WARN",
            "issue",
            "error",
            "fail",
            "alert",
            "fatal",
            "emergency"
    };

    public static List<String> anomDetect(String timeRangeBegin, String tempPath) {
        List<String[]> logs = ReadExcel.getData(Constants.logsFromGrayLog, 4);
        List<String> result = new ArrayList<>();
        String timeRangeEnd = CommonFunctions.addMinutes(timeRangeBegin, 10);
        try {
            Clustering.createCluster(tempPath, timeRangeBegin, timeRangeEnd);
            List<String[]> clusteringData = ReadExcel.getData(tempPath, 3);
            System.out.println(clusteringData.size());
            result.add("\n");
            result.add("Patterns for " + timeRangeBegin + " " + timeRangeEnd + "\n");
            int maxNumberOfPatterns = 20;
            for (String[] str : clusteringData) {
                result.add(str[1] + " appeared " + str[2] + " times\n");
                maxNumberOfPatterns--;
                if (maxNumberOfPatterns == 0) break;
            }
            List<String[]> logsInGivenTime = Filters.filterOnTimeStamp(logs, timeRangeBegin, timeRangeEnd);
            HashMap<String, HashSet<String>> patternAndLogs = new HashMap<>();
            for (String reg : regex)
                patternAndLogs.put(reg, new HashSet<>());
            System.out.println(logsInGivenTime.size());
            for (String[] row : logsInGivenTime) {
                String cureRowLog = row[2].toLowerCase();
                for (String expression : regex) {
                    if (cureRowLog.contains(expression.toLowerCase())) {
                        patternAndLogs.get(expression).add(cureRowLog);
                    }
                }
            }
            for (Map.Entry<String, HashSet<String>> entry : patternAndLogs.entrySet()) {
                if (entry.getValue().isEmpty()) continue;
                result.add("\n");
                result.add("Logs Matching to the Pattern: " + entry.getKey() + "\n");
                for (String log : entry.getValue()) {
                    result.add(log + "\n");
                }
                result.add("\n");
            }
        } catch (Exception e) {
            System.out.println("couldn't cluster the data");
            e.printStackTrace();
        }
        System.out.println(timeRangeBegin);
        System.out.println(timeRangeEnd);
        return result;
    }

    public static void detectAnomalies(String resultFolderPath, String beginTimeStamp, String endTimeStamp) {
        String beginningPath = Constants.folderPathForInfluxStats;
        TreeMap<String, String> statPath = new TreeMap<>();
        statPath.put("POD_CPU_USAGE", Constants.podCpuUsageDataPath);
        statPath.put("POD_MEMORY_USAGE", Constants.podMemoryUsageDataPath);
        statPath.put("TOMCAT_THREADS", Constants.tomCatThreadsDataPath);
        statPath.put("GRPC_THREADS", Constants.grpcThreadsDataPath);
        System.out.println(resultFolderPath);
        File folder = new File(resultFolderPath);
        if (folder.exists()) {
            CommonFunctions.deleteFolder(folder);
        }
        if (folder.mkdir()) {
            System.out.println("Folder created successfully: " + folder.getAbsolutePath());
        } else {
            System.err.println("Failed to create folder: " + folder.getAbsolutePath());
        }

        List<String> times = AnomalousTimeRangeDetector.getAnomalousTimeRanges(resultFolderPath, beginningPath, statPath, beginTimeStamp, endTimeStamp);


        int numberOfCores = Runtime.getRuntime().availableProcessors() / 5;
        ExecutorService cpuExecutor = Executors.newFixedThreadPool(numberOfCores);

        BlockingQueue<List<String>> resultQueue = new LinkedBlockingQueue<>();
        ExecutorService resultProcessor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < times.size(); i++) {

            int taskId = i;
            cpuExecutor.submit(() -> {
                String curTempPath = "temp/temp" + taskId + ".xlsx";
                System.out.println(curTempPath);
                List<String> curResult = anomDetect(times.get(taskId), curTempPath);
                resultQueue.offer(curResult);
                try {
                    Files.delete(Paths.get(curTempPath));
                } catch (Exception e) {
                    System.out.println("deleting temporary files failed");
                }
            });
        }
        resultProcessor.submit(() -> {
            int count = times.size();
            try {
                while (count > 0) {
                    List<String> result = resultQueue.take();
                    try (BufferedWriter bfw = new BufferedWriter(new FileWriter(resultFolderPath + "/AnomalyDetectionResults.txt", true))) {
                        bfw.newLine();
                        for (String row : result) {
                            bfw.write(row);
                        }
                        bfw.newLine();
                    } catch (IOException e) {
                        System.out.println("error while writing to AnomalyDetectionResults");
                    }
                    count--;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        cpuExecutor.shutdown();
        resultProcessor.shutdown();
        try {
            cpuExecutor.awaitTermination(30, TimeUnit.MINUTES);
            resultProcessor.awaitTermination(30, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("The file is not yet written and waits for executors were interrupted");
        }
    }

}