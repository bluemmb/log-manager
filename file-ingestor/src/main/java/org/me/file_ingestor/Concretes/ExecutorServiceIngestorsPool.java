package org.me.file_ingestor.Concretes;

import org.me.core.Container;
import org.me.file_ingestor.Abstracts.Ingestor;
import org.me.file_ingestor.Abstracts.IngestorsPool;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceIngestorsPool extends IngestorsPool {

    private final Integer numberOfThreads;
    private ExecutorService executorService;

    public ExecutorServiceIngestorsPool(Integer numberOfThreads) {
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
    public void addPath(Path path) {
        Ingestor ingestor = Container.get(Ingestor.class);
        ingestor.setPath(path);
        executorService.submit(ingestor);
    }
}
