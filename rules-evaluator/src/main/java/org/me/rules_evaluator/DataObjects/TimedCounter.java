package org.me.rules_evaluator.DataObjects;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;

public class TimedCounter {

    private final int keepMaxMinutes;
    public final TreeMap<String, Integer> counter;

    public TimedCounter(int keepMaxMinutes) {
        this.counter = new TreeMap<>(Collections.reverseOrder());
        this.keepMaxMinutes = keepMaxMinutes;
    }

    public void add(Date date) {
        String key = generateKey(date);
        counter.merge(key, 1, Integer::sum);
        checkSize();
    }

    private String generateKey(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    private void checkSize() {
        // TODO: This is not generally correct but works for current project usage
        while ( counter.size() > keepMaxMinutes ) {
            counter.pollLastEntry();
        }
    }

    public Integer[] reportCounts(Integer maxMinutes, Date from) {
        Integer[] counts = new Integer[maxMinutes];
        for ( int i=0 ; i<maxMinutes ; i++ ) {
            Date d = new Date(from.getTime() - (i * 60L * 1000L));
            String key = generateKey(d);
            counts[i] = counter.getOrDefault(key, 0);
        }
        return counts;
    }

    public Integer[] reportCounts(Integer maxMinutes) {
        Date now = new Date();
        return reportCounts(maxMinutes, now);
    }
}
