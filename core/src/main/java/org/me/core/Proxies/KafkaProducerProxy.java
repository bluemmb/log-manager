package org.me.core.Proxies;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.me.core.DataObjects.LogData;

public class KafkaProducerProxy {
    private KafkaProducer<String, LogData> kafkaProducer;

    public KafkaProducerProxy(KafkaProducer<String, LogData> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void send(String topic, String key, LogData logData) {
        ProducerRecord<String, LogData> producerRecord = new ProducerRecord<String, LogData>(
                topic, key, logData
        );
        try {
            kafkaProducer.send(producerRecord);
        }
        catch ( Exception e ) {
            System.out.println(e.getMessage());
        }
    }
}
