package org.me.rules_evaluator.KafkaReader;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.KafkaConsumerService;
import org.me.rules_evaluator.DataObjects.DataCollector;

public class KafkaReader implements Runnable {

    private final Dotenv dotenv;
    private final KafkaConsumerService kafkaConsumerService;
    private final DataCollector dataCollector;

    @Inject
    public KafkaReader(Dotenv dotenv, KafkaConsumerService kafkaConsumerService, DataCollector dataCollector) {
        this.dotenv = dotenv;
        this.kafkaConsumerService = kafkaConsumerService;
        this.dataCollector = dataCollector;
    }

    @Override
    public void run() {
        int pollSize = Integer.parseInt( dotenv.get("KAFKA.CONSUMER.POLL_SIZE") );
        while (true) {
            ConsumerRecords<String, LogData> records = kafkaConsumerService.poll(pollSize);
            for ( ConsumerRecord<String, LogData> record : records ) {
                System.out.println(record.key());
                dataCollector.add(record.value());
            }
            kafkaConsumerService.commitSync();
        }
    }

    public void close() {
        kafkaConsumerService.close();
    }
}
