package org.me.file_ingestor;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.me.file_ingester.DataObjects.FilenameParts;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilenameParts
{
    @ParameterizedTest
    @MethodSource("paramsFromPath")
    public void testFromPath(Path path, FilenameParts filenamePartsExpected) {
        FilenameParts filenameParts = FilenameParts.fromPath(path);
        assertEquals(filenamePartsExpected, filenameParts);
    }

    static Stream<Arguments> paramsFromPath() {
        return Stream.of(
                Arguments.arguments(
                        Paths.get("java-2021_07_12-01_55_55.log"),
                        new FilenameParts("java", "2021_07_12-01_55_55")
                ),
                Arguments.arguments(
                        Paths.get("kafka_2.3_master-2020_07_12-01_55_02.log"),
                        new FilenameParts("kafka_2.3_master", "2020_07_12-01_55_02")
                )
        );
    }
}
