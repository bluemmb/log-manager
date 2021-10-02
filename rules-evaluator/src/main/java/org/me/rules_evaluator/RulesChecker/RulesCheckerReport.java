package org.me.rules_evaluator.RulesChecker;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;

import java.util.HashMap;

public class RulesCheckerReport {
    private static final int keepMaxMinutes;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
    }

    //     <component, <type, counts>>
    public HashMap<String   , HashMap<String, Integer[]>> report;

    public RulesCheckerReport() {
        this.report = new HashMap<>();
    }

    public void addComponent(String componentName) {
        report.put(componentName, new HashMap<>());
    }

    public void addTypeReport(String componentName, String typeName, Integer[] reportCounts) {
        report.get(componentName).put(typeName, reportCounts);
    }

    public int getTypeRate(String componentName, String typeName, int minutes) {
        Integer[] counts = report.get(componentName).get(typeName);
        return rateFromArray(counts, minutes, 0);
    }

    public int getTypeMaxRate(String componentName, String typeName, int minutes) {
        Integer[] counts = report.get(componentName).get(typeName);
        int maxRate = 0;
        for ( int i=0 ; i<=Math.max(0, keepMaxMinutes-minutes) ; i++ ) {
            // TODO: Can be optimized
            int rate = sumFromArray(counts, minutes, i) / minutes;
            maxRate = Math.max(maxRate, rate);
        }
        return maxRate;
    }

    public int getComponentRate(String componentName, int minutes) {
        HashMap<String, Integer[]> component = report.get(componentName);
        int sum = 0;
        for ( Integer[] counts : component.values() ) {
            sum += sumFromArray(counts, minutes, 0);
        }
        return sum / minutes;
    }

    public int getComponentMaxRate(String componentName, int minutes) {
        HashMap<String, Integer[]> component = report.get(componentName);
        int maxRate = 0;
        for ( int i=0 ; i<=Math.max(0, keepMaxMinutes-minutes) ; i++ ) {
            int sum = 0;
            for (Integer[] counts : component.values()) {
                sum += sumFromArray(counts, minutes, i);
            }
            int rate = sum / minutes;
            maxRate = Math.max(maxRate, rate);
        }
        return maxRate;
    }

    private int rateFromArray(Integer[] counts, int minutes, int skip)
    {
        int sum = sumFromArray(counts, minutes, skip);
        return sum / minutes;
    }

    private int sumFromArray(Integer[] counts, int minutes, int skip)
    {
        int sum = 0;
        int start = Math.min(skip, counts.length-1);
        int end = Math.min(skip+minutes, counts.length-1);
        for ( int i=start ; i<=end; i++ ) {
            sum += counts[i];
        }
        return sum;
    }
}
