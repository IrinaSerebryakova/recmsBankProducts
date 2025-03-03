package com.project.command.component;

import com.project.command.model.RecomDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.project.command.repository.RecomConstants.SAVING;

@Component
public class RecomRuleSetImplToSave implements RecomRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(RecomRuleSetImplToSave.class);
    private static JdbcTemplate jdbcTemplate;

    public RecomRuleSetImplToSave(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<RecomDTO> evaluateRules(String user_id) {
        logger.info("Method \"evaluateRules\" of {} is working", RecomRuleSetImplToSave.class);
        try {
            boolean evaluate = atLeastOneProductDebit(user_id) && sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub(user_id) && addDebitMoreThanSpendDebit(user_id);
            return evaluate ? Optional.of(SAVING) : Optional.empty();
        } catch (NullPointerException e) {
            logger.error("При проверке id на соответствие набору правил выброшено NullPointerException");
        }
        return Optional.of(SAVING);
    }

    /**
     * Пользователь использует как минимум один продукт с типом DEBIT.
     */
    public static boolean atLeastOneProductDebit(String userId){
        logger.info("Rule \"atLeastOneProductDebit\" of {} is checking", RecomRuleSetImplToSave.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN NOT EXISTS (SELECT 1 FROM transactions WHERE user_id = ? AND type = 'DEBIT') THEN 0 ELSE 1 END",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"atLeastOneProductDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
     */
    public static boolean sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub(String userId) {
        logger.info("Rule \"sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub\" of {} is checking", RecomRuleSetImplToSave.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) >=" +
                            "50000 OR SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) >= 50000 " +
                            "THEN TRUE ELSE FALSE END AS condition_met " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND t.type = 'DEPOSIT';",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"sumAddDebitOrSumAddSavingMoreThanFiftyThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     */
    public static boolean addDebitMoreThanSpendDebit(String userId){
        logger.info("Rule \"addDebitMoreThanSpendDebit\" of {} is checking", RecomRuleSetImplToSave.class);
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) > " +
                            "SUM(CASE WHEN t.type = 'WITHDRAW' THEN t.amount ELSE 0 END) THEN TRUE ELSE FALSE END " +
                            "AS deposit_greater_than_withdraw FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id WHERE t.user_id = ?",
                    new Object[]{userId},
                    Boolean.class);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"addDebitMoreThanSpendDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}



