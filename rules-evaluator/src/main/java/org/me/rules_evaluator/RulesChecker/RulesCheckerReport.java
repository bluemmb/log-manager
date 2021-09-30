package org.me.rules_evaluator.RulesChecker;

import java.util.HashMap;

public class RulesCheckerReport {
    //     <component, <type, counts>>
    HashMap<String   , HashMap<String, Integer[]>> report;

    public RulesCheckerReport() {
        this.report = new HashMap<>();
    }

    public void addComponent(String componentName) {
        report.put(componentName, new HashMap<>());
    }

    public void addTypeReport(String componentName, String typeName, Integer[] reportCounts) {
        report.get(componentName).put(typeName, reportCounts);
    }
}
