package org.me.rules_evaluator.RulesChecker;

import com.google.inject.Inject;
import org.me.core.DataObjects.LogData;

import java.util.TimerTask;

public class RulesChecker extends TimerTask {

    private final RulesConfig rulesConfig;

    @Inject
    public RulesChecker(RulesConfig rulesConfig) {
        this.rulesConfig = rulesConfig;
    }

    @Override
    public void run() {
        // System.out.println("Hello from RulesChecker");
    }

    public void checkLineLevel(String component, String type, LogData logData) {
        System.out.println("RulesChecker | Got line level check");
        System.out.println(component);
        System.out.println(type);
        System.out.println(logData.message);
    }

    public void checkTypeLevel(String component, String type, Integer[] reportCounts) {
        System.out.println("RulesChecker | Got type level check");
        System.out.println(component);
        System.out.println(type);
    }

    public void checkComponentLevel(String component, Integer[] reportCounts) {
        System.out.println("RulesChecker | Got component level check");
        System.out.println(component);
    }
}
