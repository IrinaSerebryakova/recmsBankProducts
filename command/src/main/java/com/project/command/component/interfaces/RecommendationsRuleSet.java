package com.project.command.component.interfaces;

import com.project.command.model.RecommendationsDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationsRuleSet {

  Optional<RecommendationsDTO> evaluateRules(UUID userId);
}

