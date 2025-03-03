
package com.project.command.services;

import com.project.command.component.RecomRuleSet;
import com.project.command.model.RecomDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecomServiceImpl implements RecomService {

    private final List<RecomRuleSet> rules;

    public RecomServiceImpl(List<RecomRuleSet> rules) {
        this.rules = rules;
    }

    @Override
    public List<RecomDTO> getRecommendations(UUID userId) {
        return rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}

