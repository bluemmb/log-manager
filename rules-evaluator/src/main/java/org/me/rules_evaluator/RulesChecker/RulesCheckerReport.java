package org.me.rules_evaluator.RulesChecker;

import java.util.HashMap;

public class RulesCheckerReport {
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
        return rateFromArray(counts, minutes);
    }

    public int getComponentRate(String componentName, int minutes) {
        HashMap<String, Integer[]> component = report.get(componentName);
        int sum = 0;
        for ( Integer[] counts : component.values() ) {
            sum += sumFromArray(counts, minutes);
        }
        return sum / minutes;
    }

    public int rateFromArray(Integer[] counts, int minutes)
    {
        int sum = sumFromArray(counts, minutes);
        return sum / minutes;
    }

    public int sumFromArray(Integer[] counts, int minutes)
    {
        int sum = 0;
        for ( int i=0 ; i<minutes; i++ ) {
            sum += counts[i];
        }
        return sum;
    }
}
