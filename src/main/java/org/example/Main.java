import org.example.AnomalyDetector.Algorithm1;
import org.example.Filter.Filters;
import org.example.GetData.ReadCsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String path = "/Users/vardaan.dua/downloads/All-Messages-search-result(3).xlsx";

        List<String[]> data = ReadCsv.getData(path);
        try {
            Algorithm1.Algo1(path);
        }
        catch(Exception e){
            System.out.println("fat gaya");
        }
//        int cnt =20;
//        for (int i=0;i<data.size();++i) {
//            String[] values = data.get(i);
//            System.out.println("Key: " + i + ", Values: " + values);
//            cnt--;
//            if(cnt == 0) break;
//        }
        //       Filters.filterOnSource(data,"\"voice-alshaya-deployment-5cb8667fd7-knq52\"");
//        Filters.filterOnTimeStamp(data,"\"2024-06-25T06:04:33.333Z\"","\"2024-06-25T07:04:33.333Z\"");
//        Filters.filterOnTimeStamp();


    }
}
