package org.example.AnomalyDetector;

import org.example.CommonFunctions;

import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class AnomalousTimeRangeDetector {
    public static List<String> printAndGetInfo(String paramStatsPath, String parameterName , String beginTimeStamp , String endTimeStamp) {
        String resultPath = "AnomalyDetectionResults/" + parameterName + "AnomalousTimes.txt";
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(resultPath))) {
            bfw.write("Anomalous times of " + parameterName + " in IST\n");
            List<String> anomalousTimes = AnomalyDetectionAlgorithm.getAnomalousTimeRangesWRTStatistic(paramStatsPath,beginTimeStamp,endTimeStamp);
            List<String> anomalousTimesFormatted = new ArrayList<String>();
            for (String str : anomalousTimes) {
                anomalousTimesFormatted.add(CommonFunctions.convertDate(str));
            }
            for (String value : anomalousTimesFormatted) {
                bfw.write(value);
                bfw.newLine();
            }
            return anomalousTimesFormatted;
        } catch (Exception e) {
            System.out.println("fat gaya here");
            return new ArrayList<String>();
        }
    }

    public static List<String> getAnomalousTimeRanges(String beginningPath, TreeMap<String, String> statPath , String beginTimeStamp, String endTimeStamp) {
        TreeSet<String> timeRanges = new TreeSet<>();
        for (Map.Entry<String, String> entry : statPath.entrySet()) {

            timeRanges.addAll(printAndGetInfo(beginningPath + entry.getValue(), entry.getKey(),beginTimeStamp,endTimeStamp));
        }
        return new ArrayList<>(timeRanges);
    }
}
