package org.me.rules_evaluator.RulesChecker.Rule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
    public String name;
    public RuleLevelEnum level;
    public RuleRate rate;
    public RuleWhere where;

    public Rule(
            @JsonProperty("name") String name,
            @JsonProperty("level") RuleLevelEnum level,
            @JsonProperty("rate") RuleRate rate,
            @JsonProperty("where") RuleWhere where
    ) {
        this.name = name;
        this.level = level;
        this.rate = rate;
        this.where = where;
    }

    public boolean testComponent(String component) {
        return where.testComponent(component);
    }

    public boolean testComponentType(String component, String type) {
        return where.testComponentType(component, type);
    }
}
