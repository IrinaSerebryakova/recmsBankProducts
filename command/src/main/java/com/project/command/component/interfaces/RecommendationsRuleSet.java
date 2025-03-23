package com.project.command.component.interfaces;

import com.project.command.model.Rule;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationsRuleSet {
  Optional<Rule> evaluateRules(UUID userId);
}

