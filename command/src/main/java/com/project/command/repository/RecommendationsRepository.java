package com.project.command.repository;

import com.project.command.dynamic.constants.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);

    private final Map<String, Boolean> isTheUserOfTheProduct = new HashMap<>();
    private final Map<String, Boolean> isTheActiveUserOfTheProduct = new HashMap<>();
    private final Map<String, Boolean> comparingTransactionAmounts = new HashMap<>();
    private final Map<String, Boolean> comparingTheAmountOfDepositsWithWithdrawsOfOneProductType = new HashMap<>();

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * USER_OF
     * Проверяет, использует ли пользователь продукт типа productType.
     *
     * @param userId
     * @param productType
     */
    public Boolean isTheUserOfTheProduct(UUID userId, ProductType productType) {
        String key = userId.toString() + productType;
        if (isTheUserOfTheProduct.containsKey(key)) {
            return isTheUserOfTheProduct.get(key);
        }
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 0 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = ?;",
                    Boolean.class,
                    userId,
                    productType);
            logger.info("Result of method checking rule \"isTheUserOfTheProduct\" with input parameters {}, {} is {}", userId, productType, result);

            isTheUserOfTheProduct.put(key, result);

            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"isTheUserOfTheProduct\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * ACTIVE_USER_OF
     * Проверяет, что количество транзакций по продуктам у пользователя, больше или равно 5.
     *
     * @param userId
     * @param productType
     */
    public Boolean isTheActiveUserOfTheProduct(UUID userId, ProductType productType) {
        int numberToCompare = 5;
        String key = userId.toString() + productType;
        if (isTheActiveUserOfTheProduct.containsKey(key)) {
            return isTheActiveUserOfTheProduct.get(key);
        }
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > ? " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = ?;",
                    Boolean.class,
                    numberToCompare,
                    userId,
                    productType);
            logger.info("Result of method checking rule \"isTheActiveUserOfTheProduct\" with input parameters {}, {} is {}", userId, productType, result);

            isTheActiveUserOfTheProduct.put(key, result);

            return result != null && result;

        } catch (Exception e) {
            logger.error("Error in \"isTheActiveUserOfTheProduct\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }


    /**
     * TRANSACTION_SUM_COMPARE
     * Сумма {transactionType} по всем продуктам типа {productType} {operationType}, чем {String amountForComparing} ₽
     * Считает сумму транзакций у пользователя.
     *
     * @param userId
     * @param transactionType
     * @param productType
     * @param operationType
     * @param amountForComparing
     */
    public Boolean comparingTransactionAmounts(UUID userId, String transactionType, String productType, String operationType, String amountForComparing) {
        String key = userId.toString() + productType;
        if (comparingTransactionAmounts.containsKey(key)) {
            return comparingTransactionAmounts.get(key);
        }

        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT CASE " +
                            "WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = ? AND p.type = ?) ? ?" +
                            "THEN TRUE ELSE FALSE END " +
                            "FROM transactions t " +
                            "JOIN products p ON t.product_id = p.id " +
                            "WHERE t.user_id = ?;",
                    Boolean.class, transactionType, productType, operationType, amountForComparing, userId);
            logger.info("Result of method checking rule \"comparingTransactionAmounts\" is {}", result);

            comparingTransactionAmounts.put(key, result);

            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"comparingTransactionAmounts\" for userId: {}, message: {}",
                    userId, e.getMessage(), e);
            return false;
        }
    }


    /**
     * TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
     * Сравнение суммы пополнений с тратами по всем продуктам одного типа
     * @param userId
     * @param productType
     * @param operationType
     */
    public Boolean comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(UUID userId, ProductType productType, String operationType) {
        String key = userId.toString() + productType;
        if (comparingTheAmountOfDepositsWithWithdrawsOfOneProductType.containsKey(key)) {
            return comparingTheAmountOfDepositsWithWithdrawsOfOneProductType.get(key);
        }
        try{
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = ?) ? " +
                        "SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = ?) " +
                        "THEN TRUE ELSE FALSE END " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?;",
                Boolean.class,
                productType,
                operationType,
                productType);
        logger.info("Result of method checking rule \"comparingTheAmountOfDepositsWithWithdrawsOfOneProductType\" is {}", result);

        comparingTheAmountOfDepositsWithWithdrawsOfOneProductType.put(key, result);

        return result != null && result;
    } catch(
    Exception e)
    {
        logger.error("Error in \"comparingTheAmountOfDepositsWithWithdrawsOfOneProductType\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}

    public void clearCashes() {
        isTheUserOfTheProduct.clear();
        isTheActiveUserOfTheProduct.clear();
        comparingTransactionAmounts.clear();
        comparingTheAmountOfDepositsWithWithdrawsOfOneProductType.clear();
    }
}
