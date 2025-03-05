package com.project.command.repository;

import com.project.command.component.CreditRecommendationsRuleSetImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
    public class RecommendationsRepository {
        private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);

        public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public int getRandomTransactionAmount(UUID userId){
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                    Integer.class,
                    userId);
            return result != null ? result : 0;
        }

    public Optional<String> findUserNameById(UUID userId) {
        String userName = jdbcTemplate.queryForObject(
                "SELECT username FROM users t WHERE t.user_id = user_id",
                String.class);
        if (userName != null) {
            return Optional.of(userName);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
     */
    public boolean sumSpendDebitMoreOneHundredThousandsRub(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE " +
                            "WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = 'DEBIT') > 100000" +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ?;",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"sumSpendDebitMoreOneHundredThousandsRub\" is {}", result);
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
    public boolean addDebitMoreThanSpendDebit(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = 'DEBIT') > " +
                            "SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = 'DEBIT') " +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ?;",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"addDebitMoreThanSpendDebit\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"addDebitMoreThanSpendDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь не использует продукты с типом CREDIT.
     */
    public boolean noOneProductCredit(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) = 0 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = 'CREDIT';",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"noOneProductCredit\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"noOneProductCredit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь использует как минимум один продукт с типом DEBIT.
     */
    public boolean atLeastOneProductDebit(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 0 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = 'DEBIT';",
                    Boolean.class);
            logger.info("Result of method checking rule \"atLeastOneProductDebit\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"atLeastOneProductDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь не использует продукты с типом INVEST.
     */
    public boolean noOneProductInvest(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) = 0 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = 'INVEST';",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"noOneProductInvest\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"noOneProductInvest\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений продуктов с типом SAVING больше 1000 ₽.
     */
    public boolean sumOfSavingMoreThanOneThousandRub(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE " +
                            "WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = 'SAVING') > 1000" +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ?;",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"sumOfSavingMoreThanOneThousandRub\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"sumOfSavingMoreThanOneThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Пользователь использует как минимум один продукт с типом DEBIT.
     */
    public boolean atLeastOneProductDebit(UUID userId){
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 0 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = 'DEBIT';",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"atLeastOneProductDebit\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"atLeastOneProductDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
     */
    public boolean sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub(UUID userId) {
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE " +
                            "WHEN SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN amount::NUMERIC ELSE 0 END) >= 50000 " +
                            "OR SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN amount::NUMERIC ELSE 0 END) >= 50000 " +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id" +
                            "WHERE t.user_id = ?;",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"sumAddDebitOrSumAddSavingMoreThanFiftyThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     */
    public boolean addDebitMoreThanSpendDebit(UUID userId){
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = 'DEBIT') > " +
                            "SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = 'DEBIT') " +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ?;",
                    new Object[]{userId},
                    Boolean.class);
            logger.info("Result of method checking rule \"addDebitMoreThanSpendDebit\" is {}", result);
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"addDebitMoreThanSpendDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}