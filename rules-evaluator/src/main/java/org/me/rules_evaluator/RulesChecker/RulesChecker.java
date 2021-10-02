package org.me.rules_evaluator.RulesChecker;

import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.core.Container;
import org.me.core.DataObjects.LogData;
import org.me.core.Services.MysqlService;
import org.me.rules_evaluator.DataObjects.DataCollector;
import org.me.rules_evaluator.RulesChecker.Rule.Rule;
import org.me.rules_evaluator.RulesChecker.Rule.RuleLevelEnum;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RulesChecker extends TimerTask {

    private static final int keepMaxMinutes;

    static {
        Dotenv dotenv = Container.get(Dotenv.class);
        keepMaxMinutes = Integer.parseInt( dotenv.get("RULES_EVALUATOR.DATA.KEEP_MAX_MINUTES") );
    }

    private final RulesConfig rulesConfig;
    private final DataCollector dataCollector;
    private final MysqlService mysqlService;
    private final Map<String, Date> reportedRules;
    private final Set<String> fullChecked;

    @Inject
    public RulesChecker(RulesConfig rulesConfig, DataCollector dataCollector, MysqlService mysqlService) {
        this.rulesConfig = rulesConfig;
        this.dataCollector = dataCollector;
        this.mysqlService = mysqlService;
        this.reportedRules = new HashMap<>();
        this.fullChecked = new HashSet<>();
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

            if ( publish(rule, component, logData.message) ) {
                System.out.println("Created Alert : " + rule.name + " [component=" + component + "]" + " [type=" + type + "]");
            }
        }
    }


    public void checkTypeLevel(RulesCheckerReport r, String component, String type) {
        for ( Rule rule : rulesConfig.rules ) {
            if ( rule.level != RuleLevelEnum.type )
                continue;

            if ( ! rule.testComponentType(component, type) )
                continue;

            int rate = 0;
            if ( isFullCheckedBefore(rule, component, type) )
                rate = r.getTypeRate(component, type, rule.rate.interval);
            else
                rate = r.getTypeMaxRate(component, type, rule.rate.interval);

            if ( rate >= rule.rate.max ) {
                String[] messages = dataCollector.getLatestMessages(component, type, 3);

                String description =
                        "Type: " + type + "\n" +
                        "Rate: " + rate + " log/minute\n" +
                        "LatestMessages: \n" + String.join("\n", messages) + "\n";

                if ( checkedPublish(rule, component, type, description, rule.rate.interval) ) {
                    System.out.println("Created Alert : " + rule.name + " [component=" + component + "]" + " [type=" + type + "]");
                }
            }
        }
    }


    public void checkComponentLevel(RulesCheckerReport r, String component) {
        for ( Rule rule : rulesConfig.rules ) {
            if ( rule.level != RuleLevelEnum.component )
                continue;

            if ( ! rule.testComponent(component) )
                continue;

            int rate = 0;
            if ( isFullCheckedBefore(rule, component, null) )
                rate = r.getComponentRate(component, rule.rate.interval);
            else
                rate = r.getComponentMaxRate(component, rule.rate.interval);

            if ( rate >= rule.rate.max ) {
                String description = "Rate: " + rate + " log/minute\n";

                if ( checkedPublish(rule, component, null, description, rule.rate.interval) ) {
                    System.out.println("Created Alert : " + rule.name + " [component=" + component + "]");
                }
            }
        }
    }


    private boolean checkedPublish(Rule rule, String component, String type, String description, int maxMinutes) {
        synchronized ( reportedRules ) {
            String hash = generateHash(rule, component, type);
            Date now = new Date();
            if (isRecentlyPublished(hash, now, maxMinutes))
                return false;

            boolean published = publish(rule, component, description);
            if (published)
                reportedRules.put(hash, now);

            return published;
        }
    }


    private boolean publish(Rule rule, String component, String description) {
        return mysqlService.storeAlert(rule.name, component, description);
    }


    private String generateHash(Rule rule, String component, String type) {
        return rule.name + "|" + component + (type != null ? "|" + type : "");
    }


    private boolean isRecentlyPublished(String hash, Date now, int maxMinutes) {
        if ( ! reportedRules.containsKey(hash) )
            return false;

        Date publishedDate = reportedRules.get(hash);
        long duration  = now.getTime() - publishedDate.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        return diffInMinutes <= maxMinutes;
    }


    private boolean isFullCheckedBefore(Rule rule, String component, String type) {
        synchronized ( fullChecked ) {
            String hash = generateHash(rule, component, type);
            boolean checked = fullChecked.contains(hash);
            if (!checked) fullChecked.add(hash);
            return checked;
        }
    }
}
