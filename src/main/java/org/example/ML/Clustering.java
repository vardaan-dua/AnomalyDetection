package org.example.ML;

import org.example.CommonFunctions;
import java.io.BufferedReader;
import java.io.InputStreamReader;



public class  Clustering{
    private static String read(Process process){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = (new StringBuilder());
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createCluster(String beginTimeStamp , String endTimeStamp) throws Exception{
        Class<?> clazz = Clustering.class;
        java.net.URL url = clazz.getResource(clazz.getSimpleName() + ".class");
        String path = url.getPath();
        //ProcessBuilder pb = new ProcessBuilder("/Users/vardaan.dua/Desktop/AnomalyDetection/myenv/bin/python3","/Users/vardaan.dua/Desktop/AnomalyDetection/src/main/clustering.py");;
        String runner = CommonFunctions.extractPath(path,8)+"/myenv/bin/python3";
        String param = CommonFunctions.extractPath(path,8)+"/src/main/clustering.py";

        ProcessBuilder pb = new ProcessBuilder(runner,param,beginTimeStamp,endTimeStamp);
        Process process = pb.start();
        String result = read(process);
        System.out.println(result);
    }

}
