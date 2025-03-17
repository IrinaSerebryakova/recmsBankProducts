package com.project.command.component;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InvestRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static String INVEST = "Invest 500";
    private final static Logger logger = LoggerFactory.getLogger(InvestRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public InvestRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public Optional<String> evaluateRules(UUID userId) {
        try {
            boolean evaluate = recommendationsRepository.isTheUserOfTheProduct(userId, "DEBIT") &&
                    !recommendationsRepository.isTheUserOfTheProduct(userId, "INVEST") &&
                    recommendationsRepository.comparingTransactionAmounts(userId, "DEPOSIT", "SAVING", ">", 1_000);
            return Optional.ofNullable(evaluate ? recommendationsRepository.getRecommendation(INVEST) : null);
        } catch (NullPointerException e) {
            logger.error("При проверке id {} на соответствие набору правил выброшено исключение {} {}", userId, e, e.getMessage());
            return Optional.empty();
        }
    }
}
