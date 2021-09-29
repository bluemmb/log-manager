package org.me.core.Proxies;

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

public class KafkaConsumerProxy {
    private KafkaConsumer<String, LogData> kafkaConsumer;

    private KafkaConsumerProxy(KafkaConsumer<String, LogData> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public static KafkaConsumerProxy factory(String topic) {
        Dotenv dotenv = Container.get(Dotenv.class);

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, dotenv.get("KAFKA.BROKERS"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, topic + "-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LogDataDeserializer.class.getName());

        KafkaConsumer<String, LogData> kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        return new KafkaConsumerProxy(kafkaConsumer);
    }

    public ConsumerRecords<String, LogData> poll(int duration) {
        return kafkaConsumer.poll(duration);
    }
}
