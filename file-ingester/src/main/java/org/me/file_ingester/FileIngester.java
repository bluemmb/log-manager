package org.me.file_ingester;

import com.google.inject.Inject;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import org.me.file_ingester.Abstracts.IngestersPool;

import java.io.IOException;

public class FileIngester {

    private final DirectoryWatcher.Builder directoryWatcherBuilder;
    private DirectoryWatcher watcher;

    private final IngestersPool ingestersPool;

    @Inject
    public FileIngester(
            DirectoryWatcher.Builder directoryWatcherBuilder,
            IngestersPool ingestersPool
        )
    {
        this.directoryWatcherBuilder = directoryWatcherBuilder;
        this.ingestersPool = ingestersPool;
    }


    public void run() throws IOException {
        startIngesters();
        startWatchingDirectory();

        System.out.println("Enter something to finish App!");
        System.in.read();

        stopWatchingDirectory();
        awaitIngesters();
    }


    private void startIngesters() {
        ingestersPool.start();
    }


    private void awaitIngesters() {
        ingestersPool.await();
    }


    private void startWatchingDirectory() throws IOException {
        watcher = directoryWatcherBuilder
                .listener( event -> {
                    if ( event.eventType() == DirectoryChangeEvent.EventType.CREATE ) {
                        System.out.println("Watcher | New File : " + event.path());
                        ingestersPool.addPath(event.path());
                    }
                } )
                .build();

        watcher.watchAsync();
        System.out.println("Watcher | Started");
    }


    private void stopWatchingDirectory() throws IOException {
        watcher.close();
        System.out.println("Watcher | Closed");
    }
}
