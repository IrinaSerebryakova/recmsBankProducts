package com.project.command.component;

import com.project.command.model.RecomDTO;

import java.util.Optional;

public interface RecomRuleSet {
  Optional<RecomDTO> evaluateRules(String user_id);
}

