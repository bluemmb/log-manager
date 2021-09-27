package org.me.file_ingester.Concretes;

import org.me.core.Container;
import org.me.file_ingester.Abstracts.IngesterFileReader;
import org.me.file_ingester.Abstracts.IngestersPool;
import org.me.file_ingester.DataObjects.FilenameParts;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceIngestersPool extends IngestersPool {

    private final Integer numberOfThreads;
    private ExecutorService executorService;

    public ExecutorServiceIngestersPool(Integer numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public void start() {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }

    @Override
    public void await() {
        this.stop();

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        }
        catch ( InterruptedException e ) {
            System.out.println("Waited to long!");
        }
    }

    @Override
    public boolean addPath(Path path) {
        FilenameParts filenameParts = this.getFilenameParts(path);
        if ( filenameParts == null )
            return false;

        IngesterFileReader ingesterFileReader = Container.get(IngesterFileReader.class);
        ingesterFileReader.setPath(path);
        ingesterFileReader.setFilenameParts(filenameParts);
        executorService.submit(ingesterFileReader);
        return true;
    }
}
