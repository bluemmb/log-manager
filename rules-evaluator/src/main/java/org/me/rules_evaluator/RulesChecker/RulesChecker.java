package org.me.rules_evaluator.RulesChecker;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.rules_evaluator.DataObjects.DataCollector;

import java.util.TimerTask;

public class RulesChecker extends TimerTask {

    private static final int keepMaxMinutes;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
    }

    private final RulesConfig rulesConfig;
    private final DataCollector dataCollector;

    @Inject
    public RulesChecker(RulesConfig rulesConfig, DataCollector dataCollector) {
        this.rulesConfig = rulesConfig;
        this.dataCollector = dataCollector;
    }

    @Override
    public void run() {
        System.out.println("RulesChecker : Getting Data");
        RulesCheckerReport rulesCheckerReport = dataCollector.reportToRulesChecker(keepMaxMinutes);
        System.out.println("RulesChecker : Got it " + rulesCheckerReport.report.size());
    }

    public void checkLineLevel(String component, String type, LogData logData) {
        System.out.println("RulesChecker | Got line level check");
        System.out.println(component);
        System.out.println(type);
        System.out.println(logData.message);
    }

    public void checkTypeLevel(String component, String type, Integer[] reportCounts) {
        System.out.println("RulesChecker | Got type level check");
        System.out.println(component);
        System.out.println(type);
    }

    public void checkComponentLevel(String component, Integer[] reportCounts) {
        System.out.println("RulesChecker | Got component level check");
        System.out.println(component);
    }
}
