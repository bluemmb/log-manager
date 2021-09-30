package org.me.core.Services;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.me.core.DataObjects.LogData;

import java.util.concurrent.Future;

public class KafkaProducerService {
    private KafkaProducer<String, LogData> kafkaProducer;

    public KafkaProducerService(KafkaProducer<String, LogData> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public Future<RecordMetadata> send(String topic, String key, LogData logData) {
        ProducerRecord<String, LogData> producerRecord = new ProducerRecord<String, LogData>(
                topic, key, logData
        );
        try {
            return kafkaProducer.send(producerRecord);
        }
        catch ( Exception e ) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
