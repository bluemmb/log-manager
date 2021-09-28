package org.me.file_ingester.LineProcessor;

import org.me.core.DataObjects.LogData;

public abstract class LineProcessor {
    public abstract LogData process(String line);
}
