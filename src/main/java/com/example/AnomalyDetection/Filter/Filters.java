package com.example.AnomalyDetection.Filter;


import com.example.AnomalyDetection.CommonFunctions;

import java.util.ArrayList;
import java.util.List;

public class Filters {
    public static List<String[]> filterOnTimeStamp(List<String[]> data, String timeStampBeg, String timeStampEnd) {
        int firstIndexIn = 0, lastIndexIn = data.size() - 1;
        int l = 0, r = data.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (CommonFunctions.compareDates(timeStampBeg, data.get(mid)[0]) <= 0) {
                firstIndexIn = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        l = 0;
        r = data.size() - 1;
        while (l <= r) {
            int mid = (l + r) / 2;
            if (CommonFunctions.compareDates(timeStampEnd, data.get(mid)[0]) >= 0) {
                lastIndexIn = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        List<String[]> result = new ArrayList<>();
        for (int i = firstIndexIn; i <= lastIndexIn; ++i) {
            result.add(data.get(i));
        }
        return result;
    }

    public static List<String[]> filterOnSource(List<String[]> data, String source) {
        List<String[]> result = new ArrayList<>();
        for (String[] dataValue : data) {
            if ((dataValue[1]).compareTo(source) == 0) {
                result.add(dataValue);
            }
        }
        return result;
    }
}
