package org.me.core.DataObjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class LogDataDeserializer implements Deserializer<LogData> {

    @Override
    public LogData deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                System.out.println("Null received at deserializing");
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new String(data), LogData.class);
        }
        catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to LogData");
        }
    }
}
