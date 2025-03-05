package com.project.command.service.interfaces;

import com.project.command.model.DynamicRule;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DynamicRuleService {
    List<DynamicRule> createNewDynamicRuleOfRecommendations();

    List<DynamicRule> getListOfDynamicRulesOfRecommendations();

    ResponseEntity<List<DynamicRule>> deleteDynamicRuleOfRecommendations(UUID productId);
}
