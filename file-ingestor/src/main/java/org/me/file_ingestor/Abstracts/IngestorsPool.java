package org.me.file_ingestor.Abstracts;

import java.nio.file.Path;

public abstract class IngestorsPool {
    public abstract void start();
    public abstract void stop();
    public abstract void await();
    public abstract void addPath(Path path);
}
