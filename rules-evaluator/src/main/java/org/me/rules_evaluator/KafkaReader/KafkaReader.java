package org.me.rules_evaluator.KafkaReader;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Proxies.KafkaConsumerProxy;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

public class KafkaReader implements Runnable {

    private final String component;
    private final Dotenv dotenv;
    private final KafkaConsumerProxy kafkaConsumerProxy;
    private final Connection mysqlConnection;

    public KafkaReader(String component) {
        this.component = component;
        this.dotenv = Container.get(Dotenv.class);
        this.kafkaConsumerProxy = KafkaConsumerProxy.factory(component);
        this.mysqlConnection = Container.get(Connection.class);
    }

    @Override
    public void run() {
        int pollSize = Integer.parseInt( dotenv.get("KAFKA.CONSUMER.POLL_SIZE", "1000") );
        while (true) {
            ConsumerRecords<String, LogData> records = kafkaConsumerProxy.poll(pollSize);
            for ( ConsumerRecord<String, LogData> record : records ) {
                System.out.println(record.key());
                try {
                    storeLogData(record.key(), record.value());
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

    private void storeLogData(String key, LogData logData) throws SQLException {
        // TODO: Use models
        try {
            PreparedStatement prepareStatement = mysqlConnection.prepareStatement(
                    "insert into logs (`key`, `component`, `logdatetime`, `type`, `threadName`, `className`, `message`, `created_at`)" +
                            "values (?, ?, ?, ?, ?, ?, ?, ?);"
            );

            prepareStatement.setString(1, key);
            prepareStatement.setString(2, component);

            prepareStatement.setDate(3, new java.sql.Date(logData.date.getTime()));
            prepareStatement.setString(4, logData.type.toString());
            prepareStatement.setString(5, logData.threadName);
            prepareStatement.setString(6, logData.className);
            prepareStatement.setString(7, logData.message);

            prepareStatement.setDate(8, new java.sql.Date((new java.util.Date()).getTime()));

            prepareStatement.executeUpdate();
        }
        catch ( SQLIntegrityConstraintViolationException e ) {
            if ( ! e.getMessage().startsWith("Duplicate entry") )   // Pass duplicate keys
                throw e;
        }
    }
}
