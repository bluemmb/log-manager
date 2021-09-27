package org.me.file_ingester.Abstracts;

import java.nio.file.Path;

public abstract class IngestersPool {
    public abstract void start();
    public abstract void stop();
    public abstract void await();
    public abstract void addPath(Path path);
}
