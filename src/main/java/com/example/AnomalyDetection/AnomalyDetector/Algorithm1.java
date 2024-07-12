package com.example.AnomalyDetection.AnomalyDetector;


import com.example.AnomalyDetection.GetData.ReadExcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Algorithm1 {
    public static int countRowsInTimeRange(List<String[]> data, int timeRangeInSeconds , int beg) throws ParseException {
        if (data.isEmpty()) {
            return 0;
        }
        System.out.println(data.get(beg)[0]);
        // Parse the first timestamp to get the start time
        Calendar startTime = isoToCalendar(data.get(beg)[0]);
        // Calculate the end time by adding the time range to the start time
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.SECOND, timeRangeInSeconds);

//        System.out.println(calendarToISO(endTime));
        // Count the number of rows within the time range
        int count = 0;
        for (int i =  beg;i<data.size();++i) {
            String[] row = data.get(i);
            Calendar rowTime = isoToCalendar(row[0]);
            if (rowTime.compareTo(startTime) >= 0 && rowTime.compareTo(endTime) <= 0) {
                count++;
            }
            else {
                break;
            }
        }

        return count;
    }

    public static Calendar isoToCalendar(String isoDateString) throws ParseException {
        // Define the ISO date format
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            // Parse the ISO date string
            Date date = isoFormat.parse(isoDateString);

            // Create a Calendar object and set the parsed date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        }
        catch (Exception e){
            System.out.println("idhar fata");
            throw new ParseException("abc",123);
        }
    }
    public static String calendarToISO(Calendar calendar) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return isoFormat.format(calendar.getTime());
    }
    public static void Algo1 (String dataPath) throws ParseException {
        String filePath = dataPath;
        List<String[]> data = ReadExcel.getData(filePath,3);
        System.out.println(data.size());
        List<Integer> messageCount = new ArrayList<>();
        List<String> bucketStartTimes = new ArrayList<>();
        double avg = 0, stdev = 0;
        final int timeRange = 5;
        for (int i = 0; i < data.size(); ) {
            bucketStartTimes.add(data.get(i)[0]);
            int count = countRowsInTimeRange(data, timeRange, i);
            System.out.println("Beginning from " + data.get(i)[0] + " " + count);
            i += count;
            messageCount.add(count);
            avg += count;
//                break;
        }
        avg /= messageCount.size();
        for (int i = 0; i < messageCount.size(); ++i) {
            stdev += (messageCount.get(i) - avg) * (messageCount.get(i) - avg);
        }
        stdev /= messageCount.size();
        stdev = Math.sqrt(stdev);
        System.out.println("Average number of messages" + avg);
        System.out.println("stdev number of messages" + stdev);
        for (int j = 6; j <= 12; j++) {
            System.out.println("Message count anomalies with priority "+((j-6)/2.0));
            for (int i = 0; i < messageCount.size(); ++i) {
                if (messageCount.get(i) >= avg + (j / 2.0) * stdev && messageCount.get(i) <= avg + ((j + 1) / 2.0) * stdev) {
                    System.out.print("Anomaly detected in message count at :");
                    System.out.println(bucketStartTimes.get(i) + " within " + timeRange + " seconds");
                    System.out.println("Message count : "+messageCount.get(i));
                }
            }
        }



    }
}
