package org.me.rules_evaluator.RulesChecker;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.MysqlService;
import org.me.rules_evaluator.DataObjects.DataCollector;
import org.me.rules_evaluator.RulesChecker.Rule.Rule;
import org.me.rules_evaluator.RulesChecker.Rule.RuleLevelEnum;

import java.util.Set;
import java.util.TimerTask;

public class RulesChecker extends TimerTask {

    private static final int keepMaxMinutes;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
    }

    private final RulesConfig rulesConfig;
    private final DataCollector dataCollector;
    private final MysqlService mysqlService;

    @Inject
    public RulesChecker(RulesConfig rulesConfig, DataCollector dataCollector, MysqlService mysqlService) {
        this.rulesConfig = rulesConfig;
        this.dataCollector = dataCollector;
        this.mysqlService = mysqlService;
    }

    @Override
    public void run() {
        RulesCheckerReport r = dataCollector.reportToRulesChecker(keepMaxMinutes);

        Set<String> components = r.report.keySet();
        for ( String componentName : components )
        {
            Set<String> types = r.report.get(componentName).keySet();
            for ( String typeName : types ) {
                checkTypeLevel(r, componentName, typeName);
            }
            checkComponentLevel(r, componentName);
        }
    }

    public void checkLineLevel(String component, String type, LogData logData) {
        for ( Rule rule : rulesConfig.rules ) {
            if ( rule.level != RuleLevelEnum.line )
                continue;

            if ( ! rule.testComponentType(component, type) )
                continue;

            mysqlService.storeAlert(rule.name, component, logData.message);
        }
    }

    public void checkTypeLevel(RulesCheckerReport r, String component, String type) {
        for ( Rule rule : rulesConfig.rules ) {
            if ( rule.level != RuleLevelEnum.type )
                continue;

            if ( ! rule.testComponentType(component, type) )
                continue;

            int rate = r.getTypeRate(component, type, rule.rate.interval);

            if ( rate >= rule.rate.max )
                mysqlService.storeAlert(rule.name, component, "Rate : " + rate);
        }
    }

    public void checkComponentLevel(RulesCheckerReport r, String component) {
        for ( Rule rule : rulesConfig.rules ) {
            if ( rule.level != RuleLevelEnum.component )
                continue;

            if ( ! rule.testComponent(component) )
                continue;

            int rate = r.getComponentRate(component, rule.rate.interval);

            if ( rate >= rule.rate.max )
                mysqlService.storeAlert(rule.name, component, "Rate : " + rate);
        }
    }
}
