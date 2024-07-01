package org.example.ML;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;



public class  Clustering{
    private static String read(Process process){
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = (new StringBuilder());
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            return result;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String Args[]) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("/Users/vardaan.dua/Desktop/AnomalyDetection/myenv/bin/python3","/Users/vardaan.dua/Desktop/AnomalyDetection/src/main/clustering.py");;
        Process process = pb.start();
        String result = read(process);
        System.out.println(result);
    }

}
