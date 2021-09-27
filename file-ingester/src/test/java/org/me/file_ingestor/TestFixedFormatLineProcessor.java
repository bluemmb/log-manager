package org.me.file_ingestor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.me.core.DataObjects.LogData;
import org.me.file_ingester.Concretes.FixedFormatLineProcessor;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixedFormatLineProcessor {

    @ParameterizedTest
    @ValueSource(strings={
            "2021-07-12 01:22:42,114 [ThreadName] INFO package.name.ClassName - msg",
            "2021-07-12 01:22:42,200   [T-1] WARN package.name.Class_Name.Subclass - Hard To Grasp !!!!! YES",
            "2021-07-12 01:22:42,200   [T-1]   ERROR     package.name.Class_Name.Subclass-",
    })
    public void testProcess(String line) {
        FixedFormatLineProcessor fixedFormatLineProcessor = new FixedFormatLineProcessor();
        LogData logData = fixedFormatLineProcessor.process(line);
        assertNotNull(logData);
        assertNotNull(logData.date);
        assertNotNull(logData.threadName);
        assertNotNull(logData.type);
        assertNotNull(logData.className);
        assertNotNull(logData.message);
    }
}
