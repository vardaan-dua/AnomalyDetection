package com.example.AnomalyDetection.Controllers;

import com.example.AnomalyDetection.AnomalyDetector.AnomalyDetection;
import com.example.AnomalyDetection.CommonFunctions;
import com.example.AnomalyDetection.ML.Clustering;
import com.example.AnomalyDetection.RepeatingPattern.CheckRepitition;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller {
    @GetMapping(path = "/")
    public String home() {
        return "<h1>Welcome</h1>";
    }
    @GetMapping(path = "/MostOccuringPattern/{beginTimeStamp}/{endTimeStamp}")
    public String getMostOccuringPattern(@PathVariable String beginTimeStamp , @PathVariable String endTimeStamp){
          return   CheckRepitition.getTheMostOccuringPattern(beginTimeStamp,endTimeStamp);
    }
    @GetMapping(path = "/MostOccuringPatternExcluding/{beginTimeStamp}/{endTimeStamp}/{excludeList}")
    public String getMostOccuringPatternExcluding(@PathVariable String beginTimeStamp , @PathVariable String endTimeStamp , @PathVariable  String[] excludeList){
        return CheckRepitition.getTheMostOccuringPattern(beginTimeStamp,endTimeStamp,excludeList);
    }
    @GetMapping(path = "/ListMostOccuringPatterns/{beginTimeStamp}/{endTimeStamp}")
    public ResponseEntity<FileSystemResource> getListMostOccuringPatterns(@PathVariable String beginTimeStamp , @PathVariable String endTimeStamp) {
        try {
            String resultPath = "RepeatingPatternResults/representative_messages.xlsx";
            File prevFile = new File(resultPath);
            if (prevFile.exists()) {
                boolean deleted = prevFile.delete();
                if (!deleted) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            Clustering.createCluster(resultPath, beginTimeStamp, endTimeStamp);
            File file = new File(resultPath);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=" + file.getName());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    @GetMapping(path = "/AnomalyDetection/{beginTimeStamp}/{endTimeStamp}")
//    public ResponseEntity<FileSystemResource> getAnomalyDetectionResults(@PathVariable String beginTimeStamp , @PathVariable String endTimeStamp) {
//        try {
//            String resultPath = "AnomalyDetectionResults";
//            CountDownLatch latch = new CountDownLatch(1);
//            Thread anomalyDetectionThread = new Thread(() -> {
//                try {
//                    AnomalyDetection.detectAnomalies(beginTimeStamp, endTimeStamp);
//                } finally {
//                    latch.countDown();
//                }
//            });
//            anomalyDetectionThread.start();
//            latch.await(1000, TimeUnit.SECONDS);
//            CommonFunctions.zipFolder(resultPath,resultPath);
//            String resultZipPath = "AnomalyDetectionResults/AnomalyDetectionResults.zip";
//            File zipFile = new File(resultZipPath);
//            if (!zipFile.exists()) {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFile.getName());
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            return new ResponseEntity<>(new FileSystemResource(zipFile), headers, HttpStatus.OK);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }



}
