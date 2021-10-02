package org.me.rules_evaluator;

import com.google.inject.Inject;
import org.me.rules_evaluator.KafkaReader.KafkaReader;
import org.me.rules_evaluator.RulesChecker.RulesChecker;

import java.util.Timer;

public class RulesEvaluator {

    private final KafkaReader kafkaReader;
    private final RulesChecker rulesChecker;

    @Inject
    public RulesEvaluator(KafkaReader kafkaReader, RulesChecker rulesChecker) {
        this.kafkaReader = kafkaReader;
        this.rulesChecker = rulesChecker;
    }

    public void run() throws Exception {
        startKafkaReader();
        startRulesChecker();

        System.out.println("Enter something to finish App!");
        System.in.read();
    }

    private void startKafkaReader() {
        Thread thread = new Thread(kafkaReader);
        thread.start();
    }

    private void startRulesChecker() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(rulesChecker, 10 * 1000, 10 * 1000);
    }
}
