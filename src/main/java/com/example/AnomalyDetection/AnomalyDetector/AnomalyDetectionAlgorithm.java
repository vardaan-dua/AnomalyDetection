package com.example.AnomalyDetection.AnomalyDetector;


import com.example.AnomalyDetection.CommonFunctions;
import com.example.AnomalyDetection.GetData.ReadExcel;
import org.apache.commons.math3.distribution.TDistribution;

import java.util.ArrayList;
import java.util.List;


public class AnomalyDetectionAlgorithm {
    public static List<String> getAnomalousTimeRangesWRTStatistic(String path, String beginTimeStamp, String endTimeStamp) {
        final double significanceLevel = 0.99;
        List<String[]> dataTemp = ReadExcel.getData(path, 2);
        List<String[]> data = new ArrayList<>();
        for (String[] strings : dataTemp) {
            String currentDate = strings[0];
            String convertedDate = CommonFunctions.convertDate(currentDate);
            assert convertedDate != null;
            if (CommonFunctions.compareDates(convertedDate, beginTimeStamp) >= 0 && CommonFunctions.compareDates(endTimeStamp, convertedDate) >= 0)
                data.add(strings);
        }
        int dataSize = data.size();
        double[] values = new double[dataSize];
        for (int i = 0; i < dataSize; ++i) {
            values[i] = Double.parseDouble(data.get(i)[1]);
        }
        double mean = 0;
        for (int i = 0; i < dataSize; ++i) {
            mean += values[i];
        }
        mean /= dataSize;
        double sampleStdev = 0;
        for (int i = 0; i < dataSize; ++i) {
            sampleStdev += (values[i] - mean) * (values[i] - mean);
        }
        sampleStdev /= (dataSize - 1);
        sampleStdev = Math.sqrt(sampleStdev);
        double[] zscore = new double[dataSize];
        for (int i = 0; i < dataSize; ++i) {
            zscore[i] = Math.abs(values[i] - mean) / sampleStdev;
        }

        double degreesOfFreedom = dataSize - 2;
        TDistribution tDistribution = new TDistribution(degreesOfFreedom);
        double tdist = tDistribution.inverseCumulativeProbability(significanceLevel);
        double thresh = ((dataSize - 1) / Math.sqrt(dataSize)) * (Math.sqrt(tdist * tdist / (dataSize - 2 + tdist * tdist)));
        List<String> result = new ArrayList<>();
        for (int i = 0; i < dataSize; ++i) {
            if (zscore[i] > thresh) {
                result.add(data.get(i)[0]);
            }
        }
        return result;
    }
}




