
package org.me.file_ingestor;

import com.google.inject.AbstractModule;
import org.me.core.Bootstrap;
import org.me.core.Container;
import org.me.core.Interfaces.AppStarter;
import org.me.file_ingestor.Providers.DirectoryWatcherBuilderProvider;

import java.util.ArrayList;
import java.util.List;

public class Main implements AppStarter {

    public void start() {
        // Sample Injection
        Greeting greeting = Container.get(Greeting.class);
    }

    public static void main(String[] args) {
        List<AbstractModule> providers = new ArrayList<>();
        providers.add(new DirectoryWatcherBuilderProvider());

        Bootstrap.run(Main.class, providers);
    }
}
