
package org.me.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.me.core.Providers.DotEnvModule;

public final class Bootstrap {

    public static <T> void run(Class<T> theClass) {
        Injector injector = Guice.createInjector(
                new DotEnvModule()
        );

        Container.build(injector);
        Container.start(theClass);
    }
}
