package com.example.AnomalyDetection;

import com.example.AnomalyDetection.StatisticalDifference.StatDiff;

import java.util.Map;
import java.util.TreeMap;


public class Main {
    public static void main(String[] args) {
        String path = Constants.logsFromGrayLog;
        TreeMap<String, String> cur = new TreeMap<>();
        cur.put("POD_CPU_USAGE", Constants.podCpuUsageDataPath);
        cur.put("Grpc Threads Used", Constants.grpcThreadsDataPath);
        cur.put("POD_MEMORY", Constants.podMemoryUsageDataPath);
        cur.put("Tomcat Threads", Constants.tomCatThreadsDataPath);
        String folderPath = "/Users/vardaan.dua/Downloads";
        TreeMap<String, double[]> checking = StatDiff.getStatDiff(folderPath,"2024-07-05T06:33:13.573Z", "2024-07-05T12:28:12.004Z", 2, cur);
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n\n", "Parameter", "Average1", "Average2", "StDev1", "StDev2");
        for (Map.Entry<String, double[]> entry : checking.entrySet()) {
            String key = entry.getKey();
            double[] value = entry.getValue();
            System.out.printf("%-20s %-20.10f %-20.10f %-20.10f %-20.10f%n",
                    key, value[0], value[1], value[2], value[3]);
            System.out.println();
        }
        System.out.printf("%-20s %-20s %-20s%n\n", "Parameter", "Count1", "Count2");
        double[] vals = StatDiff.getLogCountStatDiff("2024-07-04T21:33:13.573Z", "2024-07-04T21:28:13.573Z", 2, path);
        System.out.printf("%-20s %-20.10f %-20.10f%n\n", "Log Count", vals[0], vals[1]);
    }
}
