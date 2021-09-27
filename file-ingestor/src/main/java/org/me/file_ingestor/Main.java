
package org.me.file_ingestor;

import com.google.inject.AbstractModule;
import org.me.core.Bootstrap;
import org.me.core.Container;
import org.me.core.Interfaces.AppStarter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main implements AppStarter {

    public void start() {
        try {
            FileIngestor fileIngestor = Container.get(FileIngestor.class);
            fileIngestor.run();
        }
        catch ( IOException e ) {
            // TODO
        }
    }

    public static void main(String[] args) {
        List<AbstractModule> providers = new ArrayList<>();
        providers.add(new Providers());

        Bootstrap.run(Main.class, providers);
    }
}
