package com.example.AnomalyDetection.KafkaProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Future;


@Component
public class KProducer {
    public void send(int type, String timeStampBegin, String timeStampEnd) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka broker
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            String timeCombined = "Begin Time : " + timeStampBegin + " End Time : " + timeStampEnd;
            ProducerRecord<String, String> record = new ProducerRecord<>("AnomalyDetectionTopic", ("" + type), timeCombined);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();
            System.out.println("Sent record : " + type + " " + timeStampBegin + " " + timeStampEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



