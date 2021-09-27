package org.me.file_ingester.Abstracts;

import java.nio.file.Path;

public abstract class IngesterFileReader implements Runnable {
    protected Path path;

    public void setPath(Path path) {
        this.path = path;
    }
}
