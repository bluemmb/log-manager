package org.me.file_ingester;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import io.methvin.watcher.DirectoryChangeEvent;
import io.methvin.watcher.DirectoryWatcher;
import org.me.file_ingester.Abstracts.IngestersPool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileIngester {

    private final Dotenv dotenv;

    private final DirectoryWatcher.Builder directoryWatcherBuilder;
    private DirectoryWatcher watcher;
    private Date watcherStartDate;

    private final IngestersPool ingestersPool;

    @Inject
    public FileIngester(
            Dotenv dotenv,
            DirectoryWatcher.Builder directoryWatcherBuilder,
            IngestersPool ingestersPool
        )
    {
        this.dotenv = dotenv;
        this.directoryWatcherBuilder = directoryWatcherBuilder;
        this.ingestersPool = ingestersPool;
    }


    public void run() throws IOException {
        startIngesters();
        startWatchingDirectory();
        loadOldFiles();

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
                        if ( ! ingestersPool.addPath(event.path()) )
                            System.out.println("Watcher | File name is invalid : " + event.path());
                    }
                } )
                .build();

        watcher.watchAsync();
        watcherStartDate = new Date();
        System.out.println("Watcher | Started");
    }


    private void stopWatchingDirectory() throws IOException {
        watcher.close();
        System.out.println("Watcher | Closed");
    }


    private void loadOldFiles() {
        File directory = new File( dotenv.get("FILE_INGESTER.LOGS_PATH") );

        File[] files = directory.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));

        for ( File file : files ) {
            Date lastModified = new Date(file.lastModified());
            if ( lastModified.compareTo(watcherStartDate) <= 0 ) {
                System.out.println("OldFile | Add File : " + file.getAbsolutePath());
                if ( ! ingestersPool.addPath(file.toPath()) )
                    System.out.println("OldFile | File name is invalid : " + file.getAbsolutePath());
            }
        }
    }
}
