package com.project.command.component.interfaces;

import com.project.command.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationsRuleSet {
  Optional<String> evaluateRules(UUID userId);
}

