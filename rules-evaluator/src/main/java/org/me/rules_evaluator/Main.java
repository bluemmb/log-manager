
package org.me.rules_evaluator;

import com.google.inject.AbstractModule;
import org.me.core.Bootstrap;
import org.me.core.Container;
import org.me.core.Interfaces.AppStarter;

import java.util.ArrayList;
import java.util.List;

public class Main implements AppStarter {

    public void start() {
        try {
            RulesEvaluator rulesEvaluator = Container.get(RulesEvaluator.class);
            rulesEvaluator.run();
        }
        catch ( Exception e ) {
            // TODO
        }
    }

    public static void main(String[] args) {
        List<AbstractModule> providers = new ArrayList<>();
        providers.add(new Providers());

        Bootstrap.run(Main.class, providers);
    }
}
