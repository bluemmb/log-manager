package org.me.rules_evaluator.RulesChecker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import io.github.cdimascio.dotenv.Dotenv;
import org.me.rules_evaluator.RulesChecker.Rule.Rule;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class RulesConfig {

    private Dotenv dotenv;
    private final List<Rule> rules;

    @Inject
    public RulesConfig(Dotenv dotenv) {
        this.dotenv = dotenv;
        this.rules = loadConfig();
    }

    private List<Rule> loadConfig() {
        String rulesPath = dotenv.get("RULES_EVALUATOR.RULES_PATH");
        ObjectMapper mapper = new ObjectMapper();
        List<Rule> rules = null;
        try {
            rules = Arrays.asList(mapper.readValue(Paths.get(rulesPath).toFile(), Rule[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rules;
    }
}
