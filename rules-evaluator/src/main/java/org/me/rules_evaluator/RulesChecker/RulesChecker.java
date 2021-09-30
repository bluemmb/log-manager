package org.me.rules_evaluator.RulesChecker;

import com.google.inject.Inject;

import java.util.TimerTask;

public class RulesChecker extends TimerTask {

    private final RulesConfig rulesConfig;

    @Inject
    public RulesChecker(RulesConfig rulesConfig) {
        this.rulesConfig = rulesConfig;
    }

    @Override
    public void run() {
        System.out.println("Hello from RulesChecker");
    }
}
