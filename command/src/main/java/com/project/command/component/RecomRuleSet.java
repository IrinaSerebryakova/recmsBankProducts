package com.project.command.component;

import com.project.command.model.RecomDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecomRuleSet {

  Optional<RecomDTO> evaluateRules(UUID user_id);
}

