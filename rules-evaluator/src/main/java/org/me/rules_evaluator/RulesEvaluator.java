package org.me.rules_evaluator;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.rules_evaluator.KafkaReader.KafkaReader;
import org.me.rules_evaluator.RulesChecker.RulesChecker;
import org.me.rules_evaluator.RulesChecker.RulesConfig;

import java.util.Timer;

public class RulesEvaluator {

    private final Dotenv dotenv;
    private final RulesConfig rulesConfig;

    @Inject
    public RulesEvaluator(Dotenv dotenv, RulesConfig rulesConfig) {
        this.dotenv = dotenv;
        this.rulesConfig = rulesConfig;
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
        RulesChecker rulesChecker = new RulesChecker(rulesConfig);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(rulesChecker, 60*1000, 60*1000);
    }
}
