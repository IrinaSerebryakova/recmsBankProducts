
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.RecommendationsDTO;
import com.project.command.service.interfaces.RecommendationsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final List<RecommendationsRuleSet> rules;

    public RecommendationsServiceImpl(List<RecommendationsRuleSet> rules) {
        this.rules = rules;
    }

    @Override
    public List<RecommendationsDTO> getRecommendations(UUID userId) {
        return rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}

