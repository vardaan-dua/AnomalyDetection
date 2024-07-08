package org.example.AnomalyDetector;
import org.example.PathEditing;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class AnomalousTimeRangeDetector {
    private static String convertDate(String istTime)  {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            inputFormat.setTimeZone(java.util.TimeZone.getTimeZone("IST"));
            Date date = inputFormat.parse(istTime);
            ZonedDateTime istDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Kolkata"));
//            ZonedDateTime utcDateTime = istDateTime.withZoneSameInstant(ZoneId.of("UTC"));
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return istDateTime.format(outputFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static List<String> printAndGetInfo(String begPath , String paramStatsPath , String parameterName){
        String resultPath = begPath+"/AnomalyDetectionResults/"+parameterName+"AnomalousTimes.txt";
        try(BufferedWriter bfw = new BufferedWriter(new FileWriter(resultPath))) {
            bfw.write("Anomalous times of " + parameterName + " in IST\n");
            List<String> anomalousTimes = AnomalyDetectionAlgorithm.getAnomalousTimeRangesWRTStatistic(paramStatsPath);
            List<String> anomalousTimesFormatted = new ArrayList<String>();
            for(String str : anomalousTimes){
                anomalousTimesFormatted.add(convertDate(str));
            }
            for (String value : anomalousTimesFormatted) {
                bfw.write(value);
                bfw.newLine();
            }
            return anomalousTimesFormatted;
        }
        catch (Exception e){
            System.out.println("fat gaya here");
            return new ArrayList<String>();
        }
    }
    public static List<String> getAnomalousTimeRanges(String beginningPath, TreeMap<String,String> statPath){

        Class<?> clazz = AnomalyDetectionAlgorithm.class;
        java.net.URL url = clazz.getResource(clazz.getSimpleName() + ".class");
        String path = url.getPath();
        System.out.println(path);
        String resultBeg = PathEditing.extractPath(path,8);

        TreeSet<String> timeRanges = new TreeSet<>();
            for(Map.Entry<String,String> entry :statPath.entrySet()){
                timeRanges.addAll(printAndGetInfo(resultBeg, beginningPath+entry.getValue(),entry.getKey()));
            }
        return new ArrayList<>(timeRanges);
    }
}
