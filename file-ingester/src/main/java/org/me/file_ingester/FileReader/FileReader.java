package org.me.file_ingester.FileReader;

import org.me.core.Proxies.KafkaProducerProxy;
import org.me.file_ingester.LineProcessor.LineProcessor;
import org.me.file_ingester.DataObjects.FilenameParts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileReader implements Runnable {
    protected Path path;
    protected FilenameParts filenameParts;
    protected LineProcessor lineProcessor;
    protected KafkaProducerProxy kafkaProducerProxy;

    public FileReader(LineProcessor lineProcessor, KafkaProducerProxy kafkaProducerProxy) {
        this.lineProcessor = lineProcessor;
        this.kafkaProducerProxy = kafkaProducerProxy;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setFilenameParts(FilenameParts filenameParts) {
        this.filenameParts = filenameParts;
    }

    public boolean deleteFile() {
        try {
            Files.delete(path);
            return true;
        }
        catch (IOException exception) {
            return false;
        }
    }
}
