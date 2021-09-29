package org.me.rules_evaluator.KafkaReader;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.me.core.DataObjects.LogData;
import org.me.core.Proxies.KafkaConsumerProxy;

public class KafkaReader implements Runnable {

    private String component;
    KafkaConsumerProxy kafkaConsumerProxy;

    public KafkaReader(String component) {
        this.component = component;
        this.kafkaConsumerProxy = KafkaConsumerProxy.factory(component);
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, LogData> records = kafkaConsumerProxy.poll(1000);
            for ( ConsumerRecord<String, LogData> record : records ) {
                System.out.printf("component = %s, offset = %d, key = %s, value = %s\n",
                        component, record.offset(), record.key(), record.value());
            }
        }
    }
}
