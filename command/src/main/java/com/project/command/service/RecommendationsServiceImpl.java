
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Recommendation;
import com.project.command.repository.DynamicRuleRepository;
import com.project.command.service.interfaces.RecommendationsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationsServiceImpl implements RecommendationsService {

    private final List<RecommendationsRuleSet> rules;

    @Autowired
    public RecommendationsServiceImpl(List<RecommendationsRuleSet> rules){
        this.rules = rules;
    }

    @Override
    public List<String> getRecommendations(UUID userId) {
        return rules.stream()
                .map(rule -> rule.evaluateRules(userId))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
