package com.example.AnomalyDetection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CommonFunctions {
    public static String extractPath(String path , int remove){
        String[] parts = path.split("/");
        int numPartsToKeep = parts.length - remove ;
        StringBuilder extractedPath = new StringBuilder();
        for (int i = 0; i < numPartsToKeep; i++) {
            extractedPath.append(parts[i]);
            if (i < numPartsToKeep - 1) {
                extractedPath.append("/");
            }
        }
        return extractedPath.toString();
    }
    public static String addMinutes(String date,int min) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String dateWithoutZ = date.substring(0, date.length() - 1);
        LocalDateTime dateTime = LocalDateTime.parse(dateWithoutZ, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        LocalDateTime newDateTime = dateTime.plusMinutes(min);
        return newDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")) + "Z";
    }

    public static String convertDate(String istTime)  {
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

    public static int compareDates(String dateTimeString1, String dateTimeString2) {
        try {
            // Extract components from the first date-time string
            int year1 = Integer.parseInt(dateTimeString1.substring(0, 4));
            int month1 = Integer.parseInt(dateTimeString1.substring(5, 7));
            int day1 = Integer.parseInt(dateTimeString1.substring(8, 10));
            int hour1 = Integer.parseInt(dateTimeString1.substring(11, 13));
            int minute1 = Integer.parseInt(dateTimeString1.substring(14, 16));
            int second1 = Integer.parseInt(dateTimeString1.substring(17, 19));
            int millisecond1 = Integer.parseInt(dateTimeString1.substring(20, 23));

            // Extract components from the second date-time string
            int year2 = Integer.parseInt(dateTimeString2.substring(0, 4));
            int month2 = Integer.parseInt(dateTimeString2.substring(5, 7));
            int day2 = Integer.parseInt(dateTimeString2.substring(8, 10));
            int hour2 = Integer.parseInt(dateTimeString2.substring(11, 13));
            int minute2 = Integer.parseInt(dateTimeString2.substring(14, 16));
            int second2 = Integer.parseInt(dateTimeString2.substring(17, 19));
            int millisecond2 = Integer.parseInt(dateTimeString2.substring(20, 23));



            // Compare each component in order
            if (year1 != year2) return Integer.compare(year1, year2);
            if (month1 != month2) return Integer.compare(month1, month2);
            if (day1 != day2) return Integer.compare(day1, day2);
            if (hour1 != hour2) return Integer.compare(hour1, hour2);
            if (minute1 != minute2) return Integer.compare(minute1, minute2);
            if (second1 != second2) return Integer.compare(second1, second2);
            return Integer.compare(millisecond1, millisecond2);
        }
        catch(NumberFormatException e){
            return -101;
        }
    }
}
