package org.me.rules_evaluator;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.rules_evaluator.KafkaReader.KafkaReader;

import java.util.ArrayList;
import java.util.List;

public class RulesEvaluator {

    private final Dotenv dotenv;
    private List<Thread> kafkaReadersThreads = new ArrayList<>();

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
        String[] components = dotenv.get("COMPONENTS").split(",");

        for ( String component : components ) {
            int consumers = Integer.parseInt( dotenv.get("COMPONENTS.CONSUMERS." + component, "1") );

            for ( int i=1 ; i<=consumers ; i++ ) {
                KafkaReader kafkaReader = new KafkaReader(component);

                Thread thread = new Thread(kafkaReader);
                thread.start();

                kafkaReadersThreads.add(thread);
            }
        }
    }

    private void startRulesChecker() {
        // ...
    }
}
