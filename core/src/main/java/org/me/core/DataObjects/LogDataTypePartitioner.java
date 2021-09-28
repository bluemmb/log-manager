package org.me.core.DataObjects;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class LogDataTypePartitioner implements Partitioner {

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        LogData logData = (LogData) value;
        return logData.type.getValue();
    }

    @Override
    public void close() {

    }
}
