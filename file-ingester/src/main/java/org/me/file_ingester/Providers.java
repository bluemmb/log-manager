package org.me.file_ingester;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.github.cdimascio.dotenv.Dotenv;
import io.methvin.watcher.DirectoryWatcher;
import org.me.file_ingester.Concretes.ExecutorServiceIngestersPool;
import org.me.file_ingester.Concretes.FixedFormatIngester;
import org.me.file_ingester.Abstracts.Ingester;
import org.me.file_ingester.Abstracts.IngestersPool;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Providers extends AbstractModule {
    @Inject
    @Provides
    public DirectoryWatcher.Builder provideDirectoryWatcherBuilder(Dotenv dotenv) {
        Path logsPath = Paths.get( dotenv.get("FILE_INGESTER.LOGS_PATH") );
        return DirectoryWatcher.builder().path(logsPath);
    }

    @Inject
    @Provides
    public IngestersPool provideIngestersPool(Dotenv dotenv) {
        Integer numberOfThreads = Integer.parseInt( dotenv.get("FILE_INGESTER.POOL.THREADS") );
        return new ExecutorServiceIngestersPool(numberOfThreads);
    }

    @Inject
    @Provides
    public Ingester providesIngester() {
        return new FixedFormatIngester();
    }
}
