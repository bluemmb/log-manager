package org.me.file_ingestor.Abstracts;

import java.nio.file.Path;

public abstract class Ingestor implements Runnable {
    protected Path path;

    public void setPath(Path path) {
        this.path = path;
    }
}
