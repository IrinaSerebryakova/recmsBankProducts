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
public class CreditRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static String CREDIT = "Простой кредит";
    private final static Logger logger = LoggerFactory.getLogger(CreditRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public CreditRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    public Optional<String> evaluateRules(UUID userId) {
        try {
            boolean evaluate = recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, "DEBIT", ">") &&
                               recommendationsRepository.isTheUserOfTheProduct(userId, "CREDIT") &&
                               recommendationsRepository.comparingTransactionAmounts(userId, "WITHDRAW", "DEBIT", ">", 100_000);

            return Optional.ofNullable(evaluate ? recommendationsRepository.getRecommendation(CREDIT) : null);
        } catch (NullPointerException e) {
            logger.error("При проверке id {} на соответствие набору правил выброшено исключение {} {}", userId, e, e.getMessage());
            return Optional.empty();
        }
    }
}
