package org.me.rules_evaluator.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;

import java.util.Date;

public class Type {

    private static final int keepMaxMessages;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMessages = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MESSAGES") );
    }

    public final TimedCounter counter;
    private final FixedSizeStack lastMessages;
    private Date latestDate;

    public Type() {
        this.counter = new TimedCounter();
        this.lastMessages = new FixedSizeStack(keepMaxMessages);
    }

    public boolean add(LogData logData)
    {
        if ( isOld(logData.date) )
            return false;

        // Add to counter
        counter.add(logData.date);

        // Add to lastMessages
        lastMessages.push(logData.message);

        // Update latestDate
        latestDate = logData.date;

        return true;
    }

    private boolean isOld(Date date) {
        if ( latestDate == null )
            return false;
        return date.compareTo(latestDate) <= 0;
    }
}
