package org.me.core.DataObjects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.text.SimpleDateFormat;

public class LogDataSerializer implements Serializer<LogData> {

    @Override
    public byte[] serialize(String s, LogData logData) {
        byte[] retVal = null;

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        objectMapper.setDateFormat(dateFormat);

        try {
            retVal = objectMapper.writeValueAsString(logData).getBytes();
        } catch (Exception exception) {
            System.out.println("Error in serializing object" + logData);
        }

        return retVal;
    }
}
