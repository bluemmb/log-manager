package org.me.core.Proxies;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.me.core.DataObjects.LogData;

import java.util.ArrayList;
import java.util.List;

public class KafkaConsumerProxy {
    private KafkaConsumer<String, LogData> kafkaConsumer;

    public KafkaConsumerProxy(KafkaConsumer<String, LogData> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public void subscribe(List<String> topics) {
        kafkaConsumer.subscribe(topics);
    }

    public void subscribe(String topic) {
        List<String> topics = new ArrayList<String>();
        topics.add(topic);
        subscribe(topics);
    }
}
