package org.me.rules_evaluator.KafkaReader;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.KafkaConsumerService;
import org.me.core.Services.MysqlService;

import java.sql.SQLException;

public class KafkaReader implements Runnable {

    private final String component;
    private final Dotenv dotenv;
    private final KafkaConsumerService kafkaConsumerService;
    private final MysqlService mysqlService;

    public KafkaReader(String component) {
        this.component = component;
        this.dotenv = Container.get(Dotenv.class);
        this.kafkaConsumerService = KafkaConsumerService.factory(component);
        this.mysqlService = Container.get(MysqlService.class);
    }

    @Override
    public void run() {
        int pollSize = Integer.parseInt( dotenv.get("KAFKA.CONSUMER.POLL_SIZE", "1000") );
        while (true) {
            ConsumerRecords<String, LogData> records = kafkaConsumerService.poll(pollSize);
            try {
                for ( ConsumerRecord<String, LogData> record : records ) {
                    System.out.println(record.key());
                    mysqlService.storeLogData(component, record.key(), record.value());
                }
                kafkaConsumerService.commitSync();
            }
            catch (SQLException e) {
                System.out.println("SQLException : " + e.getMessage());
            }
            catch (Exception e) {
                System.out.println("Database Error : " + e.getMessage());
            }
        }
    }
}
