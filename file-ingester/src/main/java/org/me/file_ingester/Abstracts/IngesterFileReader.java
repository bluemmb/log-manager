package org.me.file_ingester.Abstracts;

import org.me.core.Proxies.KafkaProducerProxy;

import java.nio.file.Path;

public abstract class IngesterFileReader implements Runnable {
    protected Path path;
    protected LineProcessor lineProcessor;
    protected KafkaProducerProxy kafkaProducerProxy;

    public IngesterFileReader(LineProcessor lineProcessor, KafkaProducerProxy kafkaProducerProxy) {
        this.lineProcessor = lineProcessor;
        this.kafkaProducerProxy = kafkaProducerProxy;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
