package com.example.AnomalyDetection.RepeatingPattern;

import com.example.AnomalyDetection.GetData.ReadExcel;
import com.example.AnomalyDetection.ML.Clustering;

import java.util.List;


public class CheckRepitition {
    public static String getTheMostOccurringPattern(String beginTimeStamp, String endTimeStamp) {
        try {
            String representative = "RepeatingPatternResults/representative_messages.xlsx";
            Clustering.createCluster(representative, beginTimeStamp, endTimeStamp);
            return (ReadExcel.getData(representative, 3).get(0))[1];
        } catch (Exception e) {
            return "(Not a repeating pattern) Error Message := No Pattern Found Matching these constraints";
        }
    }

    public static String getTheMostOccurringPattern(String beginTimeStamp, String endTimeStamp, String[] excludePatterns) {
        try {
            String representative = "RepeatingPatternResults/representative_messages.xlsx";
            Clustering.createCluster(representative, beginTimeStamp, endTimeStamp);
            List<String[]> commonPatternsSrtedByOccDesc = ReadExcel.getData(representative, 3);
            for (int i = 0; i < commonPatternsSrtedByOccDesc.size(); ++i) {
                boolean isUseful = true;
                for (int j = 0; j < excludePatterns.length; ++j) {
                    if ((commonPatternsSrtedByOccDesc.get(i)[1]).compareTo(excludePatterns[j]) == 0) {
                        isUseful = false;
                    }
                }
                if (isUseful) {
                    return (commonPatternsSrtedByOccDesc.get(i)[1]);
                }
            }
            return "(Not a repeating pattern) Error Message :- No Pattern Found Matching these constraints";
        } catch (Exception e) {
            return "(Not a repeating pattern) Error Message :+ No Pattern Found Matching these constraints";
        }
    }

}
