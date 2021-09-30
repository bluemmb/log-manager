package org.me.rules_evaluator.DataObjects;

import org.me.core.DataObjects.LogData;

import java.util.HashMap;
import java.util.Map;

public class Component {

    private final Map<String, Type> types;
    public final TimedCounter counter;

    public Component() {
        this.types = new HashMap<>();
        this.counter = new TimedCounter();
    }

    public void add(LogData logData)
    {
        if ( ! types.containsKey(logData.type) )
            types.put(logData.type, new Type());

        boolean added = types.get(logData.type).add(logData);

        if ( added ) {
            this.counter.add(logData.date);
        }
    }
}
