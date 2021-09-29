package org.me.rules_evaluator;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;

public class RulesEvaluator {

    private final Dotenv dotenv;

    @Inject
    public RulesEvaluator(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public void run() throws Exception {
        initializeDataAggregator();
        startKafkaReaders();
        startRulesChecker();

        System.out.println("Enter something to finish App!");
        System.in.read();
    }

    private void initializeDataAggregator() {
        // ...
    }

    private void startKafkaReaders() {
        // ...
    }

    private void startRulesChecker() {
        // ...
    }
}
