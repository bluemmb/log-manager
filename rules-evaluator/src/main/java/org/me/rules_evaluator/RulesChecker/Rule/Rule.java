package org.me.rules_evaluator.RulesChecker.Rule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
    private String name;
    private RuleLevelEnum level;
    private RuleRate rate;
    private RuleWhere where;

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
}
