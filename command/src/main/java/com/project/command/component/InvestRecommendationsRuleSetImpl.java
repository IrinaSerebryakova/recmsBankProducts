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

import static com.project.command.repository.RecommendationsConstants.INVEST;

@Component
public class InvestRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(InvestRecommendationsRuleSetImpl.class);
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public InvestRecommendationsRuleSetImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public Optional<RecommendationsDTO> evaluateRules(UUID userId) {
        logger.info("Method \"evaluateRules\" of {} is working", InvestRecommendationsRuleSetImpl.class);
        try{
            boolean evaluate = atLeastOneProductDebit(userId) && noOneProductInvest(userId) && sumOfSavingMoreThanOneThousandRub(userId);
            return evaluate ? Optional.of(INVEST) : Optional.empty();
        }catch (NullPointerException e) {
            logger.error("При проверке id на соответствие набору правил выброшено {}", e.getClass());
        }
        return Optional.of(INVEST);
    }
}


