package org.me.file_ingester.FileReader;

import org.me.core.Services.KafkaProducerService;
import org.me.file_ingester.LineProcessor.LineProcessor;
import org.me.file_ingester.DataObjects.FilenameParts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class FileReader implements Runnable {
    protected Path path;
    protected FilenameParts filenameParts;
    protected LineProcessor lineProcessor;
    protected KafkaProducerService kafkaProducerService;

    public FileReader(LineProcessor lineProcessor, KafkaProducerService kafkaProducerService) {
        this.lineProcessor = lineProcessor;
        this.kafkaProducerService = kafkaProducerService;
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
