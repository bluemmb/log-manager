package org.me.rules_evaluator.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.rules_evaluator.RulesChecker.RulesChecker;

import java.util.Date;

public class Type {

    private static final int keepMaxMinutes;
    private static final int keepMaxMessages;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
        keepMaxMessages = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MESSAGES") );
    }

    private final String componentName;
    private final String typeName;

    public final TimedCounter counter;
    public final FixedSizeStack lastMessages;
    private Date latestDate;
    private final RulesChecker rulesChecker;

    public Type(String componentName, String typeName) {
        this.componentName = componentName;
        this.typeName = typeName;
        this.counter = new TimedCounter(keepMaxMinutes);
        this.lastMessages = new FixedSizeStack(keepMaxMessages);
        this.rulesChecker = Container.get(RulesChecker.class);
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

        // Send to rule checker
        sendToRulesChecker(logData);

        return true;
    }

    private boolean isOld(Date date) {
        if ( latestDate == null )
            return false;
        return date.compareTo(latestDate) <= 0;
    }

    private void sendToRulesChecker(LogData logData) {
        rulesChecker.checkLineLevel(componentName, typeName, logData);
    }
}
