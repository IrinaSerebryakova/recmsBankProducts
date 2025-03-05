package com.project.command.component;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.RecommendationsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.project.command.repository.RecommendationsConstants.CREDIT;

@Component
public class CreditRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(CreditRecommendationsRuleSetImpl.class);
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditRecommendationsRuleSetImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<RecommendationsDTO> evaluateRules(UUID userId) {
        logger.info("Method \"evaluateRules\" of {} is working", CreditRecommendationsRuleSetImpl.class);
        try {
            boolean evaluate = addDebitMoreThanSpendDebit(userId) && noOneProductCredit(userId) && sumSpendDebitMoreOneHundredThousandsRub(userId);
            return evaluate ? Optional.of(CREDIT) : Optional.empty();
        } catch (NullPointerException e) {
            logger.error("При проверке id на соответствие набору правил выброшено {}", e.getClass());
        }
        return Optional.of(CREDIT);
    }
}

