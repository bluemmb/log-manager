package org.me.rules_evaluator.RulesChecker.Rule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleRate {
    public int interval;
    public int max;

    public RuleRate(
            @JsonProperty("interval") int interval,
            @JsonProperty("max") int max
    ) {
        this.interval = interval;
        this.max = max;
    }
}
