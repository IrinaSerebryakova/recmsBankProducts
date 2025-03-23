
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsService{

    private final List<RecommendationsRuleSet> rules;

    @Autowired
    private RecommendationsRepository recommendationsRepository;

    @Autowired
    public RecommendationsService(List<RecommendationsRuleSet> rules){
        this.rules = rules;
    }


    public List<Rule> getRecommendations(UUID userId) {
        return rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .filter(Optional::isPresent)
                .map(it -> it.get())
                .collect(Collectors.toList());
    }

    public void clearCashes() {
        recommendationsRepository.clearCashes();
    }
}
