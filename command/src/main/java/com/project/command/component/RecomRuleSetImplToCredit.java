package com.project.command.component;

import com.project.command.model.RecomDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.project.command.repository.RecomConstants.CREDIT;

@Component
public class RecomRuleSetImplToCredit implements RecomRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(RecomRuleSetImplToCredit.class);
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public RecomRuleSetImplToCredit(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<RecomDTO> evaluateRules(String user_id) {
        logger.info("Method \"evaluateRules\" of {} is working", RecomRuleSetImplToCredit.class);
        try {
            boolean evaluate = addDebitMoreThanSpendDebit(user_id) && noOneProductCredit(user_id) && sumSpendDebitMoreOneHundredThousandsRub(user_id);
            return evaluate ? Optional.of(CREDIT) : Optional.empty();
        } catch (NullPointerException e) {
            logger.error("При проверке id на соответствие набору правил выброшено NullPointerException");
        }
        return Optional.of(CREDIT);
    }

    /**
     * Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
     */
    public static boolean sumSpendDebitMoreOneHundredThousandsRub(String userId) {
        logger.info("Rule \"sumSpendDebitMoreOneHundredThousandsRub\" of {} is checking", RecomRuleSetImplToCredit.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN " +
                            "SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) " +
                            "> 100000 " +
                            "THEN TRUE ELSE FALSE END AS deposit_greater_than_withdraw " +
                            "FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.u;",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"sumSpendDebitMoreOneHundredThousandsRub\" for userId: {}, message: {}",
                    userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     */
    public static boolean addDebitMoreThanSpendDebit(String userId) {
        logger.info("Rule \"addDebitMoreThanSpendDebit\" of {} is checking", RecomRuleSetImplToCredit.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > " +
                            "SUM(CASE WHEN t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) THEN TRUE ELSE FALSE END " +
                            "AS deposit_greater_than_withdraw FROM transactions t " +
                            "INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ?",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"addDebitMoreThanSpendDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь не использует продукты с типом CREDIT.
     */
    public static boolean noOneProductCredit(String userId) {
        logger.info("Rule \"noOneProductCredit\" of {} is checking", RecomRuleSetImplToCredit.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM transactions WHERE user_id = ? AND type = 'CREDIT'",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"noOneProductCredit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

}

