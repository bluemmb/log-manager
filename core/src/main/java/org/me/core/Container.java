package org.me.core;

import com.google.inject.Injector;
import org.me.core.Exceptions.ContainerException;
import org.me.core.Interfaces.AppStarter;

public class Container {
    static Container instance;
    Injector injector;

    private Container(Injector injector) {
        this.injector = injector;
    }

    static void build(Injector injector) {
        instance = new Container(injector);
    }

    public static Container getInstance() {
        if ( instance == null )
            throw new ContainerException("Container is not instantiated!");

        return instance;
    }

    public static <T> void start(Class<T> theClass) {
        instance = getInstance();
        instance.injector.getInstance((Class<AppStarter>)theClass).start();
    }

    public static <T> T get(Class<T> theClass) {
        instance = getInstance();
        return (T) instance.injector.getInstance(theClass);
    }
}
