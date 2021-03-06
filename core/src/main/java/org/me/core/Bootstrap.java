
package org.me.core;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.me.core.Providers.DotEnvProvider;
import org.me.core.Providers.KafkaProvider;
import org.me.core.Providers.MysqlProvider;

import java.util.ArrayList;
import java.util.List;

public final class Bootstrap {

    public static <T> void run(Class<T> theClass, List<AbstractModule> providers) {
        providers.add(new DotEnvProvider());
        providers.add(new KafkaProvider());
        providers.add(new MysqlProvider());

        Injector injector = Guice.createInjector(providers);

        Container.build(injector);
        Container.start(theClass);
    }

    public static <T> void run(Class<T> theClass) {
        List<AbstractModule> providers = new ArrayList<>();
        run(theClass, providers);
    }
}
