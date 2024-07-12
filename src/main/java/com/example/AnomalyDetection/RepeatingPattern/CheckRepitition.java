package com.example.AnomalyDetection.RepeatingPattern;

import com.example.AnomalyDetection.GetData.ReadExcel;
import com.example.AnomalyDetection.ML.Clustering;

import java.util.List;


public class CheckRepitition {
    public static String getTheMostOccuringPattern(String beginTimeStamp , String endTimeStamp) {
        try {
              String representative = "RepeatingPatternResults/representative_messages.xlsx";
              Clustering.createCluster(representative,beginTimeStamp,endTimeStamp);
              return (ReadExcel.getData(representative,3).get(0))[1];
        } catch (Exception e) {
            return "(Not a repeating pattern) Error Message := No Pattern Found Matching these constraints";
        }
    }
    public static String getTheMostOccuringPattern(String beginTimeStamp , String endTimeStamp , String [] excludePatterns){
        try {
            String representative = "RepeatingPatternResults/representative_messages.xlsx";
            Clustering.createCluster(representative,beginTimeStamp,endTimeStamp);
            List<String[]> commonPatternsSrtedByOccDesc= ReadExcel.getData(representative,3);
            for(int i = 0; i< commonPatternsSrtedByOccDesc.size();++i){
                boolean isUseful = true ;
                for(int j =0 ;j<excludePatterns.length;++j){
                    if((commonPatternsSrtedByOccDesc.get(i)[1]).compareTo(excludePatterns[j])==0){
                        isUseful = false;
                    }
                }
                if(isUseful){
                    return (commonPatternsSrtedByOccDesc.get(i)[1]);
                }
            }
            return "(Not a repeating pattern) Error Message :- No Pattern Found Matching these constraints";
        } catch (Exception e) {
            return "(Not a repeating pattern) Error Message :+ No Pattern Found Matching these constraints";
        }
    }

//    public static void main(String Args[]){

//        String path = "/Users/vardaan.dua/downloads/All-Messages-search-result(3).xlsx";
//        List<String[]> data = Filters.filterOnTimeStamp(ReadCsv.getData(path),"2024-06-28T09:17:17.032Z","2024-06-28T09:19:51.134Z");
//        System.out.println(data.size());
//        String[] regex = {
//                          "(?i)No Available Agents to make a call in workqueue",
//                          "(?i)Exception while consuming provider recording for conversation",
//                          "(?i)not supported",
//                          "(?i)Could NOT find resource",
//                          "(?i)graphqlerror",
//                          "(?i)CLIENT_REGISTRATION_FAILED",
//                          "(?i)no custom configurators were discovered as a service",
//                          "(?i)Worker thread will flush remaining events before exiting",
//                          "(?i)error while fetching activation metrics",
//                          "(?i)dialer not expected to run here",
//                          "(?i)\"GET /health-check HTTP/1.1\" 200",
//                          "(?i)ESBUlkRequestFailureException",
//                          "(?i)ClassNotFoundException",
//                          "(?i)UncategorizedExecutionException",
//                          "(?i)SprinklrRuntimeException",
//                          "(?i)IllegalArguementException",
//                          "(?i)GoogleJsonResponseException",
//                          "(?i)LinkedInRecoverableExcpetion",
//                          "(?i)FacebookAPIException",
//                          "(?i)java.lang.RuntimeException",
//                          "(?i)java.lang.NullPointerException",
//                          "(?i)java.lang.ClassCastException",
//                          "(?i)Exception while consuming provider recording for conversation",
//                          "(?i)WARN",
//                          "(?i)issue",
//                          "(?i)error",
//                          "(?i)fail",
//                          "(?i)alert",
//                          "(?i)fatal",
//                          "(?i)java\\*Exception"
//        };
//// \\bstatus=(?!200|201|202|204|301|302|304\b)\d+\b
//        Pattern[] pattern =  new Pattern[regex.length];
//        for(int i =0 ;i< regex.length;++i){
//            pattern[i] = Pattern.compile(regex[i]);
//        }
//        HashMap<String,Integer> countOfPattern = new HashMap<>();
//        for (String reg : regex) {
//            countOfPattern.put(reg, 0);
//        }
//        for (int j =0 ;j<data.size() ; ++j) {
//            String[] values = data.get(j);
//            for (int i = 0; i < regex.length; ++i) {
//                Matcher matcher = pattern[i].matcher(values[2]);
//                 if (matcher.find()) {
//                    System.out.println("Key: " + j + ", Values: " + Arrays.toString(values));
//                     countOfPattern.put(regex[i], countOfPattern.get(regex[i]) + 1);
//                 }
//            }
//        }
//        for (Map.Entry<String, Integer> entry : countOfPattern.entrySet()) {
//            System.out.println("Pattern: " + entry.getKey() + ", Count: " + entry.getValue());
//        }
//    }
}
