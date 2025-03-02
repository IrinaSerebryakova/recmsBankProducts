package com.project.command.component;

import com.project.command.model.RecmDTO;

import java.util.Optional;

public interface RecmRuleSet {

   Optional<RecmDTO> evaluateRules(String user_id);
}
