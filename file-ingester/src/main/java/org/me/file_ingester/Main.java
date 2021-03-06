
package org.me.file_ingester;

import com.google.inject.AbstractModule;
import org.me.core.Bootstrap;
import org.me.core.Container;
import org.me.core.Interfaces.AppStarter;

import java.util.ArrayList;
import java.util.List;

public class Main implements AppStarter {

    public void start() {
        try {
            FileIngester fileIngester = Container.get(FileIngester.class);
            fileIngester.run();
        }
        catch ( Exception e ) {
            System.out.println("ERROR : " + e.toString());
        }
    }

    public static void main(String[] args) {
        List<AbstractModule> providers = new ArrayList<>();
        providers.add(new Providers());

        Bootstrap.run(Main.class, providers);
    }
}
