package org.example.Filter;

import java.util.ArrayList;
import java.util.List;

public class Filters {
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
    public static List<String[]> filterOnTimeStamp( List<String[]> data , String timeStampBeg , String timeStampEnd) {
            int firstIndexIn=0,lastIndexIn=data.size()-1;
            int l =0 , r = data.size()-1;
            while(l<=r){
               int mid = (l+r)/2;
               if(compareDates(timeStampBeg,data.get(mid)[0])<=0){
                    firstIndexIn = mid ;
                     r = mid-1;
               }
               else{
                   l=mid+1;
               }
            }
            l =0 ; r = data.size()-1;
            while(l<=r){
                int mid = (l+r)/2;
                if(compareDates(timeStampEnd,data.get(mid)[0])>=0){
                    lastIndexIn = mid ;
                    l = mid+1;
                }
                else{
                    r=mid-1;
                }
            }
            List<String []> result = new ArrayList<>();
            for(int i =firstIndexIn;i<=lastIndexIn;++i){
                result.add(data.get(i));
            }
            return result;
    }
    public static List<String[]> filterOnSource(List<String[]> data , String source){
        List<String []> result = new ArrayList<>();
        for(int i =0 ;i<data.size();++i){
            if((data.get(i)[1]).compareTo(source) == 0){
                result.add(data.get(i));
            };
        }
        return result;
    }
}
