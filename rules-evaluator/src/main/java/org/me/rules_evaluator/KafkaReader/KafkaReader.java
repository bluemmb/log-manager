package org.me.rules_evaluator.KafkaReader;

import org.me.core.Proxies.KafkaConsumerProxy;

public class KafkaReader implements Runnable {

    private String component;
    KafkaConsumerProxy kafkaConsumerProxy;

    public KafkaReader(String component) {
        this.component = component;
        this.kafkaConsumerProxy = KafkaConsumerProxy.factory(component);
    }

    @Override
    public void run() {
        System.out.println("New Thread : " + component);
    }
}
