package org.me.file_ingestor.Providers;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.methvin.watcher.DirectoryWatcher;

public class DirectoryWatcherBuilderProvider extends AbstractModule {
    @Provides
    public DirectoryWatcher.Builder provideDirectoryWatcherBuilder() {
        return DirectoryWatcher.builder();
    }
}
