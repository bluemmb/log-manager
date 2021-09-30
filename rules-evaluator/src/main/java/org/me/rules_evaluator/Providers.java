package org.me.rules_evaluator;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.rules_evaluator.RulesChecker.RulesConfig;


public class Providers extends AbstractModule {

    @Inject
    @Singleton
    public RulesConfig providesRulesConfig(Dotenv dotenv) {
        return new RulesConfig(dotenv);
    }
}
