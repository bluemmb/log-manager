package org.me.file_ingestor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.me.core.DataObjects.LogData;
import org.me.file_ingester.LineProcessor.FixedFormatLineProcessor;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestFixedFormatLineProcessor {

    @ParameterizedTest
    @MethodSource("paramsProcessResultParts")
    public void testProcessResultParts(String componentName, String line, LogData logDataExpected) {
        FixedFormatLineProcessor fixedFormatLineProcessor = new FixedFormatLineProcessor();
        LogData logData = fixedFormatLineProcessor.process(componentName, line);
        assertEquals(logDataExpected, logData);
    }

    static Stream<Arguments> paramsProcessResultParts() {
        return Stream.of(
                Arguments.arguments(
                        "java",
                        "2021-07-12 01:22:42,114 [ThreadName] INFO package.name.ClassName - msg",
                        new LogData( "java", new Date(1626036762114L), "ThreadName", "INFO", "package.name.ClassName", "msg" )
                ),
                Arguments.arguments(
                        "java",
                        "2021-07-12 01:22:42,200   [T-1] WARN package.name.Class_Name.Subclass -   Hard To Grasp !!!!! YES  ",
                        new LogData( "java", new Date(1626036762200L), "T-1", "WARN", "package.name.Class_Name.Subclass", "Hard To Grasp !!!!! YES" )
                ),
                Arguments.arguments(
                        "java",
                        "2021-07-12 01:22:42,200   [App.T-1_2]   ERROR     package.name.Class_Name.Subclass -",
                        new LogData( "java", new Date(1626036762200L), "App.T-1_2", "ERROR", "package.name.Class_Name.Subclass", "" )
                )
        );
    }
}
