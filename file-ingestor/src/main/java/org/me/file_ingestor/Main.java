
package org.me.file_ingestor;

import org.me.core.Bootstrap;
import org.me.core.Container;
import org.me.core.Interfaces.AppStarter;

public class Main implements AppStarter {

    public void start() {
        // Sample Injection
        Greeting greeting = Container.get(Greeting.class);
    }

    public static void main(String[] args) {
        Bootstrap.run(Main.class);
    }
}
