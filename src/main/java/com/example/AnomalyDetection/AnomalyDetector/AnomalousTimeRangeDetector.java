package com.example.AnomalyDetection.AnomalyDetector;


import com.example.AnomalyDetection.CommonFunctions;

import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class AnomalousTimeRangeDetector {
    public static List<String> printAndGetInfo(String resultFolderPath, String paramStatsPath, String parameterName, String beginTimeStamp, String endTimeStamp) {
        String resultPath = resultFolderPath + "/" + parameterName + "AnomalousTimes.txt";
        System.out.println("Result Path for " + parameterName + " - > " + resultPath);
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(resultPath, true))) {
            bfw.write("Anomalous times of " + parameterName + " in IST\n");
            List<String> anomalousTimes = AnomalyDetectionAlgorithm.getAnomalousTimeRangesWRTStatistic(paramStatsPath, beginTimeStamp, endTimeStamp);
            List<String> anomalousTimesFormatted = new ArrayList<String>();
            for (String timeValue : anomalousTimes) {
                anomalousTimesFormatted.add(CommonFunctions.convertDate(timeValue));
            }
            for (String timeValue : anomalousTimesFormatted) {
                bfw.write(timeValue);
                bfw.newLine();
            }
            return anomalousTimesFormatted;
        } catch (Exception e) {
            System.err.println("Writing info to Anomalous Times of a parameter file failed");
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public static List<String> getAnomalousTimeRanges(String resultFolderPath, String paramStatsFolderPath, TreeMap<String, String> statPath, String beginTimeStamp, String endTimeStamp) {
        TreeSet<String> timeRanges = new TreeSet<>();
        for (Map.Entry<String, String> entry : statPath.entrySet()) {
            String fileNameOfParamter = entry.getValue();
            String parameterName = entry.getKey();
            timeRanges.addAll(printAndGetInfo(resultFolderPath, paramStatsFolderPath + fileNameOfParamter, parameterName, beginTimeStamp, endTimeStamp));
        }
        return new ArrayList<>(timeRanges);
    }
}
