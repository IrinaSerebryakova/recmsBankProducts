package com.project.command.component.interfaces;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationsRuleSet {
  Optional<String> evaluateRules(UUID userId);
}

