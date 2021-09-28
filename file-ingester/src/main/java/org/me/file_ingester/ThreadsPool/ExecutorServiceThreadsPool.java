package org.me.file_ingester.ThreadsPool;

import org.me.core.Container;
import org.me.file_ingester.FileReader.FileReader;
import org.me.file_ingester.DataObjects.FilenameParts;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceThreadsPool extends ThreadsPool {

    private final Integer numberOfThreads;
    private ExecutorService executorService;

    public ExecutorServiceThreadsPool(Integer numberOfThreads) {
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

        FileReader fileReader = Container.get(FileReader.class);
        fileReader.setPath(path);
        fileReader.setFilenameParts(filenameParts);
        executorService.submit(fileReader);
        return true;
    }
}
