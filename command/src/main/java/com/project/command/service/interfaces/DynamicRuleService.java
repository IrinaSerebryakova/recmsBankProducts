package com.project.command.service.interfaces;

import com.project.command.model.DynamicRule;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DynamicRuleService {

    DynamicRule createNewDynamicRuleOfRecommendations(DynamicRule dynamicRule);

    List<DynamicRule> getListOfDynamicRulesOfRecommendations();

    DynamicRule deleteDynamicRuleOfRecommendations(UUID dynamicRuleId);
}
