
package com.project.command.services;

import com.project.command.model.RecomDTO;
import com.project.command.model.Rule;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecomServiceImpl implements RecomService {

    private final List<Rule> rules;

    public RecomServiceImpl(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public List<RecomDTO> getRecommendations(String userId) {
        return rules.stream()
                .map(rule -> rule.apply(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}

