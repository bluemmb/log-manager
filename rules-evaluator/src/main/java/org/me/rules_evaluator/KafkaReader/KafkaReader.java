package org.me.rules_evaluator.KafkaReader;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.KafkaConsumerService;
import org.me.rules_evaluator.DataObjects.DataCollector;

public class KafkaReader implements Runnable {

    private final String component;
    private final Dotenv dotenv;
    private final KafkaConsumerService kafkaConsumerService;
    private final DataCollector dataCollector;

    public KafkaReader(String component) {
        this.component = component;
        this.dotenv = Container.get(Dotenv.class);
        this.kafkaConsumerService = KafkaConsumerService.factory(component);
        this.dataCollector = new DataCollector();
    }

    @Override
    public void run() {
        int pollSize = Integer.parseInt( dotenv.get("KAFKA.CONSUMER.POLL_SIZE") );
        while (true) {
            ConsumerRecords<String, LogData> records = kafkaConsumerService.poll(pollSize);
            for ( ConsumerRecord<String, LogData> record : records ) {
                System.out.println(record.key());
                dataCollector.add(component, record.value());
            }
            kafkaConsumerService.commitSync();
        }
    }
}
