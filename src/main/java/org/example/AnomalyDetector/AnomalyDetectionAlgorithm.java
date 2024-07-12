package org.example.AnomalyDetector;

import org.apache.commons.math3.distribution.TDistribution;
import org.example.CommonFunctions;
import org.example.GetData.ReadExcel;

import java.util.ArrayList;
import java.util.List;

public class AnomalyDetectionAlgorithm {
    public static List<String> getAnomalousTimeRangesWRTStatistic(String path , String beginTimeStamp , String endTimeStamp){
        final int numFields = 2;
        final double significanceLevel = 0.99;
        List<String[]> dataTemp= ReadExcel.getData(path,2);
        List<String[]> data = new ArrayList<>();
        for (String[] strings : dataTemp) {
            String currentDate = strings[0];
            if (CommonFunctions.compareDates(CommonFunctions.convertDate(currentDate), beginTimeStamp) >= 0 && CommonFunctions.compareDates(endTimeStamp, CommonFunctions.convertDate(currentDate)) >= 0)
                data.add(strings);
        }
        int n = data.size();
        double[] values = new double[n];
        for(int i =0;i< n;++i){
            values[i] = Double.parseDouble(data.get(i)[1]);
        }
        double mean =0 ;
        for(int i =0 ;i<n;++i){
            mean+=values[i];
        }
        mean /= n;
        double sampleStdev =0 ;
        for(int i =0 ;i<n;++i){
            sampleStdev+=(values[i]-mean)*(values[i]-mean);
        }
        sampleStdev/= (n-1);
        sampleStdev = Math.sqrt(sampleStdev);
        double[] zscore = new double[n];
        for(int i =0 ;i<n;++i){
            zscore[i] = Math.abs(values[i]-mean)/sampleStdev;
        }

        double degreesOfFreedom = n-2;
        TDistribution tDistribution = new TDistribution(degreesOfFreedom);
        double  tdist = tDistribution.inverseCumulativeProbability(significanceLevel);
        double thresh = ((n-1)/Math.sqrt(n))*(Math.sqrt(tdist*tdist/(n-2+tdist*tdist)));
        List<String> result = new ArrayList<>() ;
        for(int i =0 ;i<n;++i) {
            if (zscore[i] > thresh ) {
                result.add(data.get(i)[0]);
            }
        }
        return result;
    }
}




