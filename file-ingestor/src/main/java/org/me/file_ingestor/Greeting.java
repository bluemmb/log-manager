package org.me.file_ingestor;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import io.methvin.watcher.DirectoryWatcher;

public class Greeting {

    @Inject
    Greeting(Dotenv env, DirectoryWatcher.Builder directoryWatcherBuilder) {
        System.out.println( "Welcome to " + env.get("APP_NAME") );
        System.out.println( directoryWatcherBuilder.getClass() );
    }
}
