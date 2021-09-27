package org.me.file_ingester.Abstracts;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.me.core.DataObjects.LogData;

import java.nio.file.Path;

public abstract class IngesterFileReader implements Runnable {
    protected Path path;
    protected LineProcessor lineProcessor;
    protected KafkaProducer<String, LogData> kafkaProducer;

    public IngesterFileReader(LineProcessor lineProcessor, KafkaProducer<String, LogData> kafkaProducer) {
        this.lineProcessor = lineProcessor;
        this.kafkaProducer = kafkaProducer;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    protected void sendToKafka(LogData logData)
    {
        ProducerRecord<String, LogData> producerRecord = new ProducerRecord<String, LogData>("test", logData.date.toString(), logData);
        kafkaProducer.send(producerRecord);
    }
}
