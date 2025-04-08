
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RecommendationsService {

    private final List<RecommendationsRuleSet> rules;
    private final RecommendationsRepository recommendationsRepository;
    private final DynamicRuleService dynamicRuleService;

    public RecommendationsService(List<RecommendationsRuleSet> rules, RecommendationsRepository recommendationsRepository, DynamicRuleService dynamicRuleService) {
        this.rules = rules;
        this.recommendationsRepository = recommendationsRepository;
        this.dynamicRuleService = dynamicRuleService;
    }

    /**
     * Получение рекомендаций по статическим и динамическим правилам
     * @param userId
     * @return список статических правил
     */
    public List<Rule> getRecommendations(UUID userId) {
        return rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public void clearCashes() {
        recommendationsRepository.clearCashes();
    }
}
