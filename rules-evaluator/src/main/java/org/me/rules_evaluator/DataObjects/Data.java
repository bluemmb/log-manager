package org.me.rules_evaluator.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Data {
    private static final int keepMaxMinutes;
    private static final int keepMaxMessages;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
        keepMaxMessages = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MESSAGES") );
    }

    //      TreeMap<Minute, Count  >
    private final TreeMap<String, Integer> counter;

    //      TreeMap<Time  , Message>
    private final TreeMap<Long  , String > lastMessages;

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
        if ( lastMessages.size() > keepMaxMessages )
            lastMessages.pollLastEntry();
    }


    private boolean isTooOld(Date date) {
        Date now = new Date();
        long diff = now.getTime() - date.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        return diffInMinutes > keepMaxMinutes;
    }


    private String generateKey(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }
}
