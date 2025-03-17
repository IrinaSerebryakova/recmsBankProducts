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
public class SavingRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static String SAVING = "Top Saving";
    private final static Logger logger = LoggerFactory.getLogger(SavingRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public SavingRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public Optional<String> evaluateRules(UUID userId) {
        try {
            boolean evaluate = recommendationsRepository.isTheUserOfTheProduct(userId, "DEBIT") &&
                    (recommendationsRepository.comparingTransactionAmounts(userId, "DEPOSIT", "DEBIT", ">=", 50_000) ||
                            recommendationsRepository.comparingTransactionAmounts(userId, "DEPOSIT", "SAVING", ">=", 50_000)) &&
                    recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, "DEBIT", ">");
            return Optional.ofNullable(evaluate ? recommendationsRepository.getRecommendation(SAVING) : null);
        } catch (NullPointerException e) {
            logger.error("При проверке id {} на соответствие набору правил выброшено исключение {} {}", userId, e, e.getMessage());
            return Optional.empty();
        }
    }
}



