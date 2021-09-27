package org.me.file_ingestor;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.github.cdimascio.dotenv.Dotenv;
import io.methvin.watcher.DirectoryWatcher;
import org.me.file_ingestor.Concretes.ExecutorServiceIngestorsPool;
import org.me.file_ingestor.Concretes.FixedFormatIngestor;
import org.me.file_ingestor.Abstracts.Ingestor;
import org.me.file_ingestor.Abstracts.IngestorsPool;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Providers extends AbstractModule {
    @Inject
    @Provides
    public DirectoryWatcher.Builder provideDirectoryWatcherBuilder(Dotenv dotenv) {
        Path logsPath = Paths.get( dotenv.get("FILE_INGESTOR.LOGS_PATH") );
        return DirectoryWatcher.builder().path(logsPath);
    }

    @Inject
    @Provides
    public IngestorsPool provideIngestorsPool(Dotenv dotenv) {
        Integer numberOfThreads = Integer.parseInt( dotenv.get("FILE_INGESTOR.POOL.THREADS") );
        return new ExecutorServiceIngestorsPool(numberOfThreads);
    }

    @Inject
    @Provides
    public Ingestor providesIngestor() {
        return new FixedFormatIngestor();
    }
}
