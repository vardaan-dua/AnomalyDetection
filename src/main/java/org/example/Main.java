import org.example.RepeatingPattern.CheckRepitition;
import org.example.StatisticalDifference.StatDiff;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        String path = "/Users/vardaan.dua/downloads/All-Messages-search-result (6).xlsx";
        TreeMap<String,String> cur = new TreeMap<>();
        cur.put("POD_CPU_USAGE","/Users/vardaan.dua/downloads/Pod CPU .xlsx");
        cur.put("Grpc Threads Used","/Users/vardaan.dua/downloads/Grpc threads used.xlsx");
        cur.put("POD_MEMORY","/Users/vardaan.dua/downloads/Pod Memory.xlsx");
        cur.put("Tomcat Threads","/Users/vardaan.dua/downloads/Tomcat Threads.xlsx");
        TreeMap<String,double[]> checking = StatDiff.getStatDiff("2024-07-04T21:33:13.573Z","2024-07-04T21:28:12.004Z",2,cur);
        System.out.printf("%-20s %-20s %-20s %-20s %-20s%n\n", "Parameter", "Average1", "Average2", "StDev1", "StDev2");
        for (Map.Entry<String, double[]> entry : checking.entrySet()) {
            String key = entry.getKey();
            double[] value = entry.getValue();
            System.out.printf("%-20s %-20.10f %-20.10f %-20.10f %-20.10f%n",
                    key, value[0], value[1], value[2], value[3]);
            System.out.println();
        }
        System.out.printf("%-20s %-20s %-20s%n\n", "Parameter", "Count1", "Count2");
        double[] vals = StatDiff.getLogCountStatDiff("2024-07-04T21:33:13.573Z","2024-07-04T21:28:13.573Z",2,path);
        System.out.printf("%-20s %-20.10f %-20.10f%n\n","Log Count",vals[0],vals[1]);
        //        TreeMap<String,double[]> checking = StatDiff.getStatDiff("2024-07-05T13:58:00.000Z","2024-07-05T14:06:00.004Z",2,cur);
        //        String[] exclude ={"no available agents to make a call in workqueue"};
//        System.out.println(CheckRepitition.getTheMostOccuringPattern("2024-06-28T09:17:17.032Z", "2024-06-28T09:19:07.344Z",exclude));
//        System.out.println(CheckRepitition.getTheMostOccuringPattern("2024-07-04T20:47:05.781Z","2024-07-05T05:44:14.908Z"));
//
//        List<String[]> data = ReadCsv.getData(path);
//        try {
//            Algorithm1.Algo1(path);
//        }
//        catch(Exception e){
//            System.out.println("fat gaya");
//        }
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
