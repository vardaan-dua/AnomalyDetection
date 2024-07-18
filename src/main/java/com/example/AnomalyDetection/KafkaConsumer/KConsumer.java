//package com.example.AnomalyDetection.KafkaConsumer;
//
//
//import com.example.AnomalyDetection.AnomalyDetector.AnomalyDetection;
//import com.example.AnomalyDetection.CommonFunctions;
//import com.example.AnomalyDetection.ML.Clustering;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.stereotype.Service;
//import java.io.File;
//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//import java.util.concurrent.*;
//
//@Service
//public class KConsumer{
//    static int counterAnomalyDetection =1;
//    static int counterPatternRecognition = 1;
//
//
//    public static void main(String[] Args) {
//        Properties consumerProperties = new Properties();
//        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
//        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "query-consumers");
//        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
//        KafkaConsumer consumer = new KafkaConsumer<String,String>(consumerProperties);
//        consumer.subscribe(Collections.singletonList("topic2"));
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        scheduler.scheduleAtFixedRate(consumer, 0, 5, TimeUnit.SECONDS);
//        try {
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//                for (ConsumerRecord<String, String> record : records) {
//                    System.out.printf("Consumed record with key %s, value %s, from partition %d, offset %d%n",
//                            record.key(), record.value(), record.partition(), record.offset());
//                    if(record.key().equals("1")){
//                        String timeStampBegin = CommonFunctions.extractBeginTime(record.value());
//                        String timeStampEnd = CommonFunctions.extractEndTime(record.value());
//                        String resultFolderPath = "AnomalyDetectionResults"+counterAnomalyDetection;
//                        counterAnomalyDetection++;
//                        ExecutorService executorService = Executors.newFixedThreadPool(1);
//                        Callable<Void> anomalyDetectionTask = () -> {
//                            try {
//                                AnomalyDetection.detectAnomalies(resultFolderPath, timeStampBegin, timeStampEnd);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return null;
//                        };
//                        Future<Void> future = executorService.submit(anomalyDetectionTask);
//                        try {
//                            future.get(1000, TimeUnit.SECONDS);
//                        } catch (TimeoutException e) {
//                            System.out.println("The anomaly detection task timed out.");
//                        } catch (InterruptedException | ExecutionException e) {
//                            e.printStackTrace();
//                        } finally {
//                            executorService.shutdown();
//                        }
//                        System.out.println("Result Published");
//                    }
//                    if(record.key().equals("2")){
//                        String resultPath = "RepeatingPatternResults/representative_messages"+counterPatternRecognition+".xlsx";
//                        File prevFile = new File(resultPath);
//                          if (prevFile.exists()) {
//                            boolean deleted = prevFile.delete();
//                            if (!deleted) {
//                                System.out.println("deleting representative_messages failed");
//                                System.exit(0);
//                            }
//                          }
//                        String timeStampBegin = CommonFunctions.extractBeginTime(record.value());
//                        String timeStampEnd = CommonFunctions.extractEndTime(record.value());
//                        Clustering.createCluster(resultPath, timeStampBegin, timeStampEnd);
//                        counterPatternRecognition++;
//                        System.out.println("Result Published");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            consumer.close();
//        }
//    }
//}
//

package com.example.AnomalyDetection.KafkaConsumer;

import com.example.AnomalyDetection.AnomalyDetector.AnomalyDetection;
import com.example.AnomalyDetection.CommonFunctions;
import com.example.AnomalyDetection.ML.Clustering;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.*;

@Service
public class KConsumer implements Runnable {
    private static int counterAnomalyDetection = 1;
    private static int counterPatternRecognition = 1;

    private final KafkaConsumer<String, String> consumer;

    public KConsumer() {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "query-consumers");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        this.consumer = new KafkaConsumer<>(consumerProperties);
        this.consumer.subscribe(Collections.singletonList("AnomalyDetectionTopic"));
    }

    @Override
    public void run() {
        try {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Consumed record with key %s, value %s, from partition %d, offset %d%n",
                        record.key(), record.value(), record.partition(), record.offset());

                if ("1".equals(record.key())) {
                    String timeStampBegin = CommonFunctions.extractBeginTime(record.value());
                    String timeStampEnd = CommonFunctions.extractEndTime(record.value());
                    String resultFolderPath = "AnomalyDetectionResults" + counterAnomalyDetection++;
                    ExecutorService executorService = Executors.newFixedThreadPool(1);
                    Callable<Void> anomalyDetectionTask = () -> {
                        try {
                            AnomalyDetection.detectAnomalies(resultFolderPath, timeStampBegin, timeStampEnd);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    };
                    Future<Void> future = executorService.submit(anomalyDetectionTask);
                    try {
                        future.get(1000, TimeUnit.SECONDS);
                    } catch (TimeoutException e) {
                        System.out.println("The anomaly detection task timed out.");
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    } finally {
                        executorService.shutdown();
                    }
                    System.out.println("Result Published");
                } else if ("2".equals(record.key())) {
                    String resultPath = "RepeatingPatternResults/representative_messages" + counterPatternRecognition + ".xlsx";
                    File prevFile = new File(resultPath);
                    if (prevFile.exists() && !prevFile.delete()) {
                        System.out.println("deleting representative_messages failed");
                        System.exit(0);
                    }
                    String timeStampBegin = CommonFunctions.extractBeginTime(record.value());
                    String timeStampEnd = CommonFunctions.extractEndTime(record.value());
                    Clustering.createCluster(resultPath, timeStampBegin, timeStampEnd);
                    counterPatternRecognition++;
                    System.out.println("Result Published");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        KConsumer consumer = new KConsumer();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(consumer, 0, 2, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }));
    }
}

