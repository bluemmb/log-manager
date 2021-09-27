package org.me.file_ingester.Abstracts;

import java.nio.file.Path;

public abstract class IngesterFileReader implements Runnable {
    protected Path path;

    protected LineProcessor lineProcessor;

    public IngesterFileReader(LineProcessor lineProcessor) {
        this.lineProcessor = lineProcessor;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
