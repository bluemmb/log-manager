package org.me.file_ingester.Abstracts;

import org.me.file_ingester.DataObjects.FilenameParts;

import java.nio.file.Path;

public abstract class IngestersPool {
    public abstract void start();
    public abstract void stop();
    public abstract void await();
    public abstract boolean addPath(Path path);

    protected FilenameParts getFilenameParts(Path path) {
        return FilenameParts.fromPath(path);
    }
}
