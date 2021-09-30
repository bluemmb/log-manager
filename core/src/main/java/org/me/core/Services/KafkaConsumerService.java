package org.me.core.Services;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.DataObjects.LogDataDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerService {
    private KafkaConsumer<String, LogData> kafkaConsumer;

    private KafkaConsumerService(KafkaConsumer<String, LogData> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public static KafkaConsumerService factory(String topic) {
        Dotenv dotenv = Container.get(Dotenv.class);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, dotenv.get("KAFKA.BROKERS"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, topic + "-consumer-group");
        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, false);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LogDataDeserializer.class.getName());

        KafkaConsumer<String, LogData> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        return new KafkaConsumerService(kafkaConsumer);
    }

    public ConsumerRecords<String, LogData> poll(int duration) {
        return kafkaConsumer.poll(duration);
    }

    public void commitSync() {
        kafkaConsumer.commitSync();
    }
}
