package org.me.file_ingester;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import io.github.cdimascio.dotenv.Dotenv;
import io.methvin.watcher.DirectoryWatcher;
import org.me.core.Proxies.KafkaProducerProxy;
import org.me.file_ingester.FileReader.FileReader;
import org.me.file_ingester.LineProcessor.LineProcessor;
import org.me.file_ingester.ThreadsPool.ExecutorServiceThreadsPool;
import org.me.file_ingester.FileReader.BufferedFileReader;
import org.me.file_ingester.ThreadsPool.ThreadsPool;
import org.me.file_ingester.LineProcessor.FixedFormatLineProcessor;

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
    public ThreadsPool providesThreadsPool(Dotenv dotenv) {
        Integer numberOfThreads = Integer.parseInt( dotenv.get("FILE_INGESTER.POOL.THREADS") );
        return new ExecutorServiceThreadsPool(numberOfThreads);
    }

    @Inject
    @Provides
    public FileReader providesFileReader(LineProcessor lineProcessor, KafkaProducerProxy kafkaProducerProxy) {
        return new BufferedFileReader(lineProcessor, kafkaProducerProxy);
    }

    @Provides
    public LineProcessor providesLineProcessor() {
        return new FixedFormatLineProcessor();
    }
}
