
package com.project.command.service;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Recommendation;
import com.project.command.repository.RecommendationsRepository;
import com.project.command.service.interfaces.RecommendationsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationsServiceImpl implements RecommendationsService {

    private final List<RecommendationsRuleSet> rules;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RecommendationsRepository recommendationsRepository;

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

    @Override
    public Recommendation createRecommendation(Recommendation recommendation) {
        recommendationsRepository.createRecommendation(recommendation);
        return recommendation;
    }
}
