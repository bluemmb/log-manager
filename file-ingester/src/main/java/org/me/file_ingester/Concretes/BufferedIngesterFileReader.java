package org.me.file_ingester.Concretes;

import org.me.core.DataObjects.LogData;
import org.me.core.Proxies.KafkaProducerProxy;
import org.me.file_ingester.Abstracts.IngesterFileReader;
import org.me.file_ingester.Abstracts.LineProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BufferedIngesterFileReader extends IngesterFileReader {

    public BufferedIngesterFileReader(LineProcessor lineProcessor, KafkaProducerProxy kafkaProducerProxy) {
        super(lineProcessor, kafkaProducerProxy);
    }

    @Override
    public void run() {
        System.out.println("Ingester | Started : " + path.getFileName());

        File file = path.toFile();
        try ( BufferedReader bufferedReader = new BufferedReader( new FileReader(file) ) )
        {
            String line;

            while ( (line = bufferedReader.readLine()) != null ) {
                System.out.println("Ingester | Line : " + line);
                LogData logData = lineProcessor.process(line);
                kafkaProducerProxy.send(logData);
            }
        }
        catch (Exception e) {
            System.out.println("Ingester | Error : " + e.getMessage());
        }

        System.out.println("Ingester | Finished : " + path.getFileName());
    }
}
