package org.me.rules_evaluator.RulesChecker;

import java.util.Arrays;
import java.util.HashMap;

public class RulesCheckerReport {

    //     <component, <type, counts>>
    public int maxMinutes;
    public HashMap<String   , HashMap<String, Integer[]>> report;
    public HashMap<String, Integer[]> reportComponents;

    public RulesCheckerReport(int maxMinutes) {
        this.report = new HashMap<>();
        this.reportComponents = new HashMap<>();
        this.maxMinutes = maxMinutes;
    }

    public void addComponent(String componentName) {
        report.put(componentName, new HashMap<>());
    }

    public void addTypeReport(String componentName, String typeName, Integer[] reportCounts) {
        report.get(componentName).put(typeName, reportCounts);
    }

    public void finish() {
        report.forEach( (componentName, component) -> {
            Integer[] componentCounts = new Integer[maxMinutes];
            Arrays.fill(componentCounts, 0);
            component.forEach( (typeName, counts) -> {
                componentCounts[0] += counts[0];
                for (int i=1 ; i<maxMinutes ; i++) {
                    counts[i] += counts[i-1];
                    componentCounts[i] += counts[i];
                }
            } );
            reportComponents.put(componentName, componentCounts);
        });
    }


    public int getTypeRate(String componentName, String typeName, int minutes) {
        Integer[] counts = report.get(componentName).get(typeName);
        return rateFromArray(counts, minutes+1, 0);
    }


    public int getTypeMaxRate(String componentName, String typeName, int minutes) {
        Integer[] counts = report.get(componentName).get(typeName);
        return maxRateFromArray(counts, minutes+1);
    }


    public int getComponentRate(String componentName, int minutes) {
        Integer[] counts = reportComponents.get(componentName);
        return rateFromArray(counts, minutes+1, 0);
    }


    public int getComponentMaxRate(String componentName, int minutes) {
        Integer[] counts = reportComponents.get(componentName);
        return maxRateFromArray(counts, minutes+1);
    }


    public int maxRateFromArray(Integer[] counts, int minutes)
    {
        int maxRate = 0;
        int len = counts.length;

        for ( int i=0 ; i<len ; i++ ) {
            maxRate = Math.max(maxRate, rateFromArray(counts, minutes, i));
        }

        return maxRate;
    }


    public int rateFromArray(Integer[] counts, int minutes, int skip)
    {
        int sum = sumFromArray(counts, minutes, skip);
        return sum / minutes;
    }


    public int sumFromArray(Integer[] counts, int minutes, int skip)
    {
        int start = Math.max(0, skip);
        int end = Math.min(skip+minutes-1, counts.length-1);
        if ( end < start ) return 0;
        if ( end >= maxMinutes ) return 0;
        return counts[end] - (start == 0 ? 0 : counts[start-1]);
    }
}
