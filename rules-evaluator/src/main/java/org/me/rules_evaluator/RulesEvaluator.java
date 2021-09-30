package org.me.rules_evaluator;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.rules_evaluator.KafkaReader.KafkaReader;

import java.util.ArrayList;
import java.util.List;

public class RulesEvaluator {

    private final Dotenv dotenv;

    @Inject
    public RulesEvaluator(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public void run() throws Exception {
        startKafkaReaders();
        startRulesChecker();

        System.out.println("Enter something to finish App!");
        System.in.read();
    }

    private void startKafkaReaders() {
        KafkaReader kafkaReader = new KafkaReader();
        Thread thread = new Thread(kafkaReader);
        thread.start();
    }

    private void startRulesChecker() {
        // ...
    }
}
