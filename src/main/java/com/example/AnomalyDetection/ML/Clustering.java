package com.example.AnomalyDetection.ML;


import com.example.AnomalyDetection.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Clustering {
    private static String read(Process process) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = (new StringBuilder());
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCluster(String resultPath, String beginTimeStamp, String endTimeStamp) throws Exception {
        String runner = "myenv/bin/python3";
        String param = "src/main/clustering.py";
        ProcessBuilder pb = new ProcessBuilder(runner, param, Constants.logsFromGrayLog, resultPath, beginTimeStamp, endTimeStamp);
        Process process = pb.start();
        String result = read(process);
        System.out.println(result);
    }

}
