package org.me.file_ingestor;

import com.google.inject.Inject;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import org.me.file_ingestor.Abstracts.IngestorsPool;

import java.io.IOException;

public class FileIngestor {

    private final DirectoryWatcher.Builder directoryWatcherBuilder;
    private DirectoryWatcher watcher;

    private final IngestorsPool ingestorsPool;

    @Inject
    public FileIngestor(
            DirectoryWatcher.Builder directoryWatcherBuilder,
            IngestorsPool ingestorsPool
        )
    {
        this.directoryWatcherBuilder = directoryWatcherBuilder;
        this.ingestorsPool = ingestorsPool;
    }


    public void run() throws IOException {
        startIngestors();
        startWatchingDirectory();

        System.out.println("Enter something to finish App!");
        System.in.read();

        stopWatchingDirectory();
        awaitIngestors();
    }


    private void startIngestors() {
        ingestorsPool.start();
    }


    private void awaitIngestors() {
        ingestorsPool.await();
    }


    private void startWatchingDirectory() throws IOException {
        watcher = directoryWatcherBuilder
                .listener( event -> {
                    if ( event.eventType() == DirectoryChangeEvent.EventType.CREATE ) {
                        System.out.println("Watcher | New File : " + event.path());
                        ingestorsPool.addPath(event.path());
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
