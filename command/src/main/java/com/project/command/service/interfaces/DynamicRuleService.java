package com.project.command.service.interfaces;

import com.project.command.model.DynamicRule;

import java.util.List;
import java.util.Optional;

public interface DynamicRuleService {

    void createNewDynamicRuleOfRecommendations(DynamicRule dynamicRule);

    List<DynamicRule> getListOfDynamicRulesOfRecommendations();

    Optional<DynamicRule> deleteDynamicRuleOfRecommendations(Long dynamicRuleId);
}
