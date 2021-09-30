package org.me.rules_evaluator.RulesChecker.Rule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleWhere {
    public String component;
    public String type;

    public RuleWhere (
            @JsonProperty("component") String component,
            @JsonProperty("type") String type
    ) {
        this.component = component;
        this.type = type;
    }
}
