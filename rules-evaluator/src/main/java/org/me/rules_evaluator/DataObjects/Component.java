package org.me.rules_evaluator.DataObjects;

import org.me.core.DataObjects.LogData;

import java.util.HashMap;
import java.util.Map;

public class Component {

    private final String componentName;

    public final Map<String, Type> types;

    public Component(String componentName) {
        this.componentName = componentName;
        this.types = new HashMap<>();
    }

    public void add(LogData logData)
    {
        String type = logData.type;

        if ( ! types.containsKey(type) )
            types.put(type, new Type(componentName, type));

        types.get(type).add(logData);
    }
}
