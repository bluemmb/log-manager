package org.me.rules_evaluator.DataObjects;

import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.rules_evaluator.RulesChecker.RulesCheckerReport;

import java.util.HashMap;
import java.util.Map;

public class DataCollector {
    private static final int keepMaxMinutes;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
    }

    private final Map<String, Component> components;

    public DataCollector() {
        this.components = new HashMap<>();
    }

    public synchronized void add(LogData logData)
    {
        String componentName = logData.componentName;

        if ( ! components.containsKey(componentName) )
            components.put(componentName, new Component(componentName));

        components.get(componentName).add(logData);
    }

    public synchronized RulesCheckerReport reportToRulesChecker(int maxMinutes) {
        RulesCheckerReport rulesCheckerReport = new RulesCheckerReport(keepMaxMinutes);
        components.forEach( (componentName, component) -> {
            rulesCheckerReport.addComponent(componentName);
            component.types.forEach( (typeName, type) -> {
                rulesCheckerReport.addTypeReport(componentName, typeName, type.counter.reportCounts(maxMinutes));
            } );
        } );
        rulesCheckerReport.finish();
        return rulesCheckerReport;
    }

    public String[] getLatestMessages(String component, String type, int maxCount) {
        return components.get(component).types.get(type).lastMessages.read(maxCount);
    }
}
