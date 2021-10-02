package org.me.core.Services;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.me.core.DataObjects.LogData;

public class KafkaConsumerService {
    private KafkaConsumer<String, LogData> kafkaConsumer;

    public KafkaConsumerService(KafkaConsumer<String, LogData> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public ConsumerRecords<String, LogData> poll(int duration) {
        return kafkaConsumer.poll(duration);
    }

    public void commitSync() {
        kafkaConsumer.commitSync();
    }

    public void close() {
        kafkaConsumer.close();
    }
}
