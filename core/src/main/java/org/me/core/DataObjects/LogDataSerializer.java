package org.me.core.DataObjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class LogDataSerializer implements Serializer<LogData> {
    @Override
    public byte[] serialize(String s, LogData logData) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(logData).getBytes();
        } catch (Exception exception) {
            System.out.println("Error in serializing object" + logData);
        }
        return retVal;
    }
}
