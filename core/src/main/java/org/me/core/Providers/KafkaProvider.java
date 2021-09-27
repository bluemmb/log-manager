package org.me.core.Providers;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.me.core.DataObjects.LogData;
import org.me.core.DataObjects.LogDataSerializer;
import org.me.core.Proxies.KafkaProducerProxy;

import java.util.Properties;

public class KafkaProvider extends AbstractModule {
    @Inject
    @Provides
    @Singleton
    public KafkaProducer<String, LogData> providesKafkaProducer(Dotenv dotenv) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, dotenv.get("KAFKA.BROKERS"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, dotenv.get("KAFKA.CLIENT_ID"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LogDataSerializer.class.getName());
        return new KafkaProducer<String, LogData>(props);
    }

    @Inject
    @Provides
    @Singleton
    public KafkaProducerProxy providesKafkaProducerProxy(KafkaProducer<String, LogData> kafkaProducer) {
        return new KafkaProducerProxy(kafkaProducer);
    }
}
