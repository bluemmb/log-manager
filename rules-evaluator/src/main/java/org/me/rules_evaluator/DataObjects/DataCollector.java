package org.me.rules_evaluator.DataObjects;

import org.me.core.DataObjects.LogData;

import java.util.HashMap;

public class DataCollector {
    //            HashMap<component, HashMap<LogType, Data>>
    private final HashMap<String   , HashMap<String , Data>> database;


    public DataCollector() {
        this.database = new HashMap<>();
    }


    public void add(LogData logData)
    {
        Data data = locateData(logData.componentName, logData.type);
        data.add(logData);
    }


    private Data locateData(String component, String type)
    {
        if ( ! database.containsKey(component) )
            database.put(component, new HashMap<>());
        HashMap<String, Data> componentData = database.get(component);

        if ( ! componentData.containsKey(type) )
            componentData.put(type, new Data());
        Data data = componentData.get(type);

        return data;
    }
}
