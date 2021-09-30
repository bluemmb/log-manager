package org.me.rules_evaluator.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

public class Type {
    private static final int keepMaxMinutes;
    private static final int keepMaxMessages;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
        keepMaxMessages = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MESSAGES") );
    }

    //            TreeMap<Minute, Count  >
    public final TreeMap<String, Integer> counter;
    private final FixedSizeStack lastMessages;
    private Date latestDate;

    public Type() {
        this.counter = new TreeMap<>();
        this.lastMessages = new FixedSizeStack(keepMaxMessages);
    }

    public void add(LogData logData)
    {
        if ( isOld(logData.date) )
            return;

        // Add to counter
        String key = generateKey(logData.date);
        counter.merge(key, 1, Integer::sum);

        // Add to lastMessages
        lastMessages.push(logData.message);

        // Update latestDate
        latestDate = logData.date;
    }


    private boolean isOld(Date date) {
        if ( latestDate == null )
            return false;
        return date.compareTo(latestDate) <= 0;
    }


    private String generateKey(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }
}
