package org.example.AnomalyDetector;

import org.example.Filter.Filters;
import org.example.GetData.ReadExcel;
import org.example.ML.Clustering;
import org.example.CommonFunctions;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.example.CommonFunctions.addMinutes;

public class AnomalyDetection {
    final static String[] regex = {
            "ESBUlkRequestFailureException",
            "ClassNotFoundException",
            "UncategorizedExecutionException",
            "SprinklrRuntimeException",
            "IllegalArguementException",
            "GoogleJsonResponseException",
            "LinkedInRecoverableExcpetion",
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
            "fatal"
    };
    public static void main(String[] Args) {
        String beginningPath = "/Users/vardaan.dua/Downloads/";
        TreeMap<String,String> statPath = new TreeMap<>();
        statPath.put("POD_CPU_USAGE","Pod CPU .xlsx");
        statPath.put("POD_MEMORY_USAGE","Pod Memory.xlsx");
        statPath.put("TOMCAT_THREADS","Tomcat Threads.xlsx");
        statPath.put("GRPC_THREADS","Grpc threads used.xlsx");
        List<String> times = AnomalousTimeRangeDetector.getAnomalousTimeRanges(beginningPath,statPath);
        Class<?> clazz = AnomalyDetection.class;
        java.net.URL url = clazz.getResource(clazz.getSimpleName() + ".class");
        String path = url.getPath();
        System.out.println(path);
        String resultPath = CommonFunctions.extractPath(path, 8) + "/AnomalyDetectionResults/AnomalyDetectionResult.txt";
        System.out.println(resultPath);
        List<String[]> logs =ReadExcel.getData("/Users/vardaan.dua/downloads/All-Messages-search-result (6).xlsx",4);
//        Pattern[] patterns = getPatterns(regex);
//        List<String> result ;
        for (String time : times) {
            String rangeBegin = time;
            String rangeEnd = CommonFunctions.addMinutes(time,10);
            BufferedWriter bfw= null;
            try {
                    Clustering.createCluster(rangeBegin, rangeEnd);
                    String readFrom = CommonFunctions.extractPath(path,8)+"/representative_messages.xlsx";
                    List<String[]> clusteringData = ReadExcel.getData(readFrom,3);
                    System.out.println(clusteringData.size());
                    bfw = new BufferedWriter(new FileWriter(resultPath,true));
                    bfw.newLine();
                    bfw.write("Patterns for "+rangeBegin+" "+rangeEnd+"\n");
                    int count =20;
                    for(String[] str : clusteringData){
                        for(int i =1 ;i<str.length;++i){
                            bfw.write(str[i]+" ");
                        }
                        bfw.newLine();
                        count--;
                        if(count == 0)break;
                    }
                    List<String[]> logsInGivenTime = Filters.filterOnTimeStamp(logs,rangeBegin,rangeEnd);
                    HashMap<String, HashSet<String>> pattern_Logs = new HashMap<>();
                    for(String reg : regex)
                        pattern_Logs.put(reg,new HashSet<>());
                    System.out.println(logsInGivenTime.size());
                    for(String[] row : logsInGivenTime){
                        for(int i =0 ;i<regex.length;++i) {
                            if((row[2].toLowerCase()).contains(regex[i].toLowerCase())){
                                pattern_Logs.get(regex[i]).add(row[2]);
                            }
                        }
                    }
                    for (Map.Entry<String, HashSet<String>> entry : pattern_Logs.entrySet()) {
                        bfw.newLine();
                        bfw.write("Pattern: " + entry.getKey());
                        bfw.newLine();
                        if(entry.getValue().isEmpty())continue;
                        for (String log : entry.getValue()) {
                            bfw.write(log);
                            bfw.newLine();
                        }
                        bfw.newLine(); // Separate each pattern block with a blank line
                    }
            }
            catch (Exception e){
                System.out.println("couldn't cluster the data");
            }
            finally {
                if (bfw != null) {
                    try {
                        bfw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(rangeBegin);
            System.out.println(rangeEnd);
        }
    }
}
