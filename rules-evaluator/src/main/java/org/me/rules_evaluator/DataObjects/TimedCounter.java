package org.me.rules_evaluator.DataObjects;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class TimedCounter {

    public final TreeMap<String, Integer> counter;

    public TimedCounter() {
        this.counter = new TreeMap<>();
    }

    public void add(Date date) {
        String key = generateKey(date);
        counter.merge(key, 1, Integer::sum);
    }

    private String generateKey(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public Integer[] reportCounts(Integer maxMinutes) {
        Integer[] counts = new Integer[maxMinutes];
        Date now = new Date();
        for ( int i=0 ; i<maxMinutes ; i++ ) {
            Date d = new Date(now.getTime() - (i * 60L * 1000L));
            String key = generateKey(d);
            counts[i] = counter.getOrDefault(key, 0);
        }
        return counts;
    }
}
