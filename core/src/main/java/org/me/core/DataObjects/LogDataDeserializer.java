package org.me.core.DataObjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.text.SimpleDateFormat;

public class LogDataDeserializer implements Deserializer<LogData> {

    @Override
    public LogData deserialize(String topic, byte[] data) {
        LogData object = null;

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        objectMapper.setDateFormat(dateFormat);

        try {
            object = objectMapper.readerFor(LogData.class).readValue(data);
        } catch (Exception exception) {
            System.out.println("Error in deserializing bytes "+ exception);
        }

        return object;
    }
}
