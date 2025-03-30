
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsService{

    private final List<RecommendationsRuleSet> rules;
    private final RecommendationsRepository recommendationsRepository;
private final DynamicRuleService dynamicRuleService;

    public RecommendationsService(List<RecommendationsRuleSet> rules, RecommendationsRepository recommendationsRepository, DynamicRuleService dynamicRuleService) {
        this.rules = rules;
        this.recommendationsRepository = recommendationsRepository;
        this.dynamicRuleService = dynamicRuleService;
    }

    //здесь только статика надо добавить динамику
    public List<Rule> getRecommendations(UUID userId) {
        List<Rule> rules1 = dynamicRuleService.getListOfRulesForUser(userId);
        List<Rule> rules2 = rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .filter(Optional::isPresent)
                .map(it -> it.get())
                .collect(Collectors.toList());
        List<Rule> common = new ArrayList<>(rules1);
        common.addAll(rules2);
        return common;
    }

    public void clearCashes() {
        recommendationsRepository.clearCashes();
    }
}
