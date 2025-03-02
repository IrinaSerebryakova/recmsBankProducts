package com.project.command.component;

import com.project.command.model.RecmDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.project.command.repository.RecmConstants.INVEST;

@Component
public class RecmRuleSetImplToInvest implements RecmRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(RecmRuleSetImplToInvest.class);
    private static JdbcTemplate jdbcTemplate;

    public RecmRuleSetImplToInvest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<RecmDTO> evaluateRules(String user_id) {
        logger.info("Method \"evaluateRules\" of {} is working", RecmRuleSetImplToInvest.class);
        try{
            boolean evaluate = atLeastOneProductDebit(user_id) && noOneProductInvest(user_id) && sumOfSavingMoreThanOneThousandRub(user_id);
            return evaluate ? Optional.of(INVEST) : Optional.empty();
        }catch (NullPointerException e) {
            logger.error("При проверке id на соответствие набору правил выброшено NullPointerException");
        }
        return Optional.of(INVEST);
    }

    /**
     * Пользователь использует как минимум один продукт с типом DEBIT.
     */
    public static boolean atLeastOneProductDebit(String userId) {
        logger.info("Rule \"atLeastOneProductDebit\" of {} is checking", RecmRuleSetImplToInvest.class);
    try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN NOT EXISTS (SELECT 1 FROM transactions WHERE user_id = ? AND type = 'DEBIT') THEN 0 ELSE 1 END",
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"atLeastOneProductDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь не использует продукты с типом INVEST.
     */
    public static boolean noOneProductInvest(String userId) {
        logger.info("Rule \"noOneProductInvest\" of {} is checking", RecmRuleSetImplToInvest.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) = 0 FROM transactions WHERE user_id = :userId AND type = 'INVEST')",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"noOneProductInvest\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений продуктов с типом SAVING больше 1000 ₽.
     */
    public static boolean sumOfSavingMoreThanOneThousandRub(String userId) {
        logger.info("Rule \"sumOfSavingMoreThanOneThousandRub\" of {} is checking", RecmRuleSetImplToInvest.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT SUM(amount) FROM transactions WHERE user_id = :userId AND type = 'SAVING'",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"sumOfSavingMoreThanOneThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}


