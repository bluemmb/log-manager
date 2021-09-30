package org.me.rules_evaluator.RulesChecker.Rule;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleWhere {

    public List<String> componentsList;
    public List<String> typesList;

    public RuleWhere (
            @JsonProperty("component") String component,
            @JsonProperty("type") String type
    ) {
        this.componentsList = listFromString(component);
        this.typesList = listFromString(type);
    }

    private List<String> listFromString(String string) {
        if ( string == null )
            return new ArrayList<>();

        String[] array = string.split(",");
        List<String> list = new ArrayList<>();
        for ( String s : array ) list.add( s.trim().toLowerCase() );
        return list;
    }

    public boolean testComponent(String component) {
        return componentsList.contains("*") || componentsList.contains(component.trim().toLowerCase());
    }

    public boolean testType(String type) {
        return typesList.contains("*") || typesList.contains(type.trim().toLowerCase());
    }

    public boolean testComponentType(String component, String type) {
        return testComponent(component) && testType(type);
    }
}
