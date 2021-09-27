package org.me.file_ingester.Concretes;

import org.me.core.Container;
import org.me.file_ingester.Abstracts.Ingester;
import org.me.file_ingester.Abstracts.IngestersPool;

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
    public void addPath(Path path) {
        Ingester ingester = Container.get(Ingester.class);
        ingester.setPath(path);
        executorService.submit(ingester);
    }
}