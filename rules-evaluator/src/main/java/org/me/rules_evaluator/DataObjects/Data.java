package org.me.rules_evaluator.DataObjects;

import org.me.core.DataObjects.LogData;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Data {

    //      TreeMap<Minute, Count  >
    private TreeMap<String, Integer> counter;

    //      TreeMap<Time  , Message>
    private TreeMap<Long  , String > lastMessages;

    public Data() {
        this.counter = new TreeMap<>();
        this.lastMessages = new TreeMap<>(Collections.reverseOrder());
    }

    public void add(LogData logData)
    {
        if ( isTooOld(logData.date) )
            return;

        // Add to counter
        String key = generateKey(logData.date);
        counter.merge(key, 1, Integer::sum);

        // Add to lastMessages
        lastMessages.put(logData.date.getTime(), logData.message);
        if ( lastMessages.size() > 10 )
            lastMessages.pollLastEntry();
    }


    private boolean isTooOld(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return diffInMinutes > 30;
    }


    private String generateKey(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }
}
