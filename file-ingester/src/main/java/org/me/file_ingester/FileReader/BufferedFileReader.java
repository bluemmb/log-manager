package org.me.file_ingester.FileReader;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.KafkaProducerService;
import org.me.file_ingester.LineProcessor.LineProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BufferedFileReader extends FileReader {

    public BufferedFileReader(LineProcessor lineProcessor, KafkaProducerService kafkaProducerService) {
        super(lineProcessor, kafkaProducerService);
    }

    @Override
    public void run() {
        System.out.println("Ingester | Started : " + path.getFileName());

        File file = path.toFile();
        try ( BufferedReader bufferedReader = new BufferedReader( new java.io.FileReader(file) ) )
        {
            String line;
            List<Future<?>> futures = new ArrayList<>();

            while ( (line = bufferedReader.readLine()) != null )
            {
                line = line.trim();
                if ( line.length() == 0 ) continue;
                // System.out.println("Ingester | Line : " + line);

                LogData logData = lineProcessor.process(line);
                String key = filenameParts.datetimeString + "--" + logData.date.getTime();

                Future<?> future = kafkaProducerService.send(filenameParts.componentName, key, logData);
                futures.add(future);
            }

            boolean cancelled = false;
            int waitTime = Integer.parseInt( Container.get(Dotenv.class).get("FILE_INGESTER.POOL.THREADS.WAITTIME_FOR_FUTURES") );
            for ( Future<?> future : futures ) {
                if ( cancelled ) {
                    future.cancel(true);
                    continue;
                }

                try {
                    future.get(waitTime, TimeUnit.MILLISECONDS);
                }
                catch ( Exception e ) {
                    cancelled = true;
                    future.cancel(true);
                }
            }

            if ( cancelled )
                throw new Exception("Could not write all file data to kafka!");

            if ( ! deleteFile() )
                throw new Exception("Data was fully written to kafka, but couldn't delete the file!");

            System.out.println("Ingester | Finished : " + path.getFileName());
        }
        catch (Exception e) {
            System.out.println("Ingester | Error : " + e.getMessage());
        }
    }
}
