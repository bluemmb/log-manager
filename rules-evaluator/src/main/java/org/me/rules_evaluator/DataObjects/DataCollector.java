package org.me.rules_evaluator.DataObjects;

import org.me.core.DataObjects.LogData;

import java.util.HashMap;
import java.util.Map;

public class DataCollector {

    private final Map<String, Component> components;

    public DataCollector() {
        this.components = new HashMap<>();
    }

    public void add(LogData logData)
    {
        String componentName = logData.componentName;

        if ( ! components.containsKey(componentName) )
            components.put(componentName, new Component(componentName));

        components.get(componentName).add(logData);
    }
}
