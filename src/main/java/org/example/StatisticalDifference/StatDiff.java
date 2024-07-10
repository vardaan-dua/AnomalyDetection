package org.example.StatisticalDifference;

import org.example.CommonFunctions;
import org.example.Filter.Filters;
import org.example.GetData.ReadExcel;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTEmImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.example.CommonFunctions.convertDate;

public class StatDiff {
    public static double mean(double[] data) throws ArithmeticException {
        if(data.length == 0) throw  new ArithmeticException();
        double result = 0;
        for(double x : data)result+=x;
        result/=data.length;
        return result;
    }
    public static double stdev(double[] data) throws ArithmeticException{
      if(data.length == 0) throw new ArithmeticException();
      double result =0, mean = mean(data);
      for(double x : data) result+=Math.pow(x-mean,2);
      result/=data.length;
      result = Math.sqrt(result);
      return result;
    }
    public static double[] getData(List<String[]> tempData, String timeRangeBeg , String timeRangeEnd){
        double[] data = new double[tempData.size()];
        List<String[]> useful = new ArrayList<>();
        for(int i =0 ;i<tempData.size();++i){
            String date = CommonFunctions.convertDate(tempData.get(i)[0]);
            if(CommonFunctions.compareDates(date,timeRangeBeg)>=0 && CommonFunctions.compareDates(timeRangeEnd,date)>=0) {
                double value =  Double.parseDouble(tempData.get(i)[1]);
                data[i]=value;
            }
        }
        return data;
    }
    public static double[] getLogCountStatDiff(String timeRangeBeg1 , String timeRangeBeg2 , int durationInMinutes , String path ){
        String timeRangeEnd1 = CommonFunctions.addMinutes(timeRangeBeg1,durationInMinutes);
        String timeRangeEnd2 = CommonFunctions.addMinutes(timeRangeBeg2,durationInMinutes);
        return getLogCountStatDiff(timeRangeBeg1,timeRangeEnd1,timeRangeBeg2,timeRangeEnd2,path);
    }
    public static double[] getLogCountStatDiff(String timeRangeBeg1 , String timeRangeEnd1 , String timeRangeBeg2 , String timeRageEnd2 , String path){
        List<String[]> tempData = ReadExcel.getData(path,4);
        int num1 = Filters.filterOnTimeStamp(tempData,timeRangeBeg1,timeRangeEnd1).size();
        int num2 = Filters.filterOnTimeStamp(tempData,timeRangeBeg2,timeRageEnd2).size();
        return new double[]{num1,num2};
    }

    public static TreeMap<String,double[]> getStatDiff(String timeRangeBeg1 , String timeRangeBeg2 , int durationInMinutes, TreeMap<String,String> paramPath){
        String timeRangeEnd1 = CommonFunctions.addMinutes(timeRangeBeg1,durationInMinutes);
        String timeRangeEnd2 = CommonFunctions.addMinutes(timeRangeBeg2,durationInMinutes);
        return getStatDiff(timeRangeBeg1,timeRangeEnd1,timeRangeBeg2,timeRangeEnd2,paramPath);
    }
    public static TreeMap<String,double[]> getStatDiff(String timeRangeBeg1 , String timeRangeEnd1 , String timeRangeBeg2,String timeRangeEnd2, TreeMap<String,String> paramPath){
        TreeMap<String,double[]> result = new TreeMap<>();
        for(var entry : paramPath.entrySet()){
            String param = entry.getKey();
            String path = entry.getValue();
            List<String[]> tempData = ReadExcel.getData(path,2);
            double[] values1 = getData(tempData,timeRangeBeg1,timeRangeEnd1);
            double[] values2 = getData(tempData,timeRangeBeg2,timeRangeEnd2);
            double[] res = {mean(values1),mean(values2),stdev(values1),stdev(values2)};
            result.put(param,res);
        }
        return result;
    }
}
