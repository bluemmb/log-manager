package org.me.core.Proxies;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.me.core.DataObjects.LogData;

public class KafkaProducerProxy {
    private KafkaProducer<String, LogData> kafkaProducer;

    public KafkaProducerProxy(KafkaProducer<String, LogData> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void sendToKafka(LogData logData) {
        ProducerRecord<String, LogData> producerRecord = new ProducerRecord<String, LogData>("test", logData.date.toString(), logData);
        kafkaProducer.send(producerRecord);
    }
}
