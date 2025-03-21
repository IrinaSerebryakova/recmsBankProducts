package com.project.command.repository;

import com.project.command.model.DynamicRule;
import com.project.command.model.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RecommendationsRepository{
    private int counterOfRecommendations;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);
    private final DynamicRuleRepository dynamicRuleRepository;
    private List<String> arguments;
    private DynamicRule dynamicRule;

    public RecommendationsRepository(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public int getCounterOfRecommendations() {
        counterOfRecommendations = jdbcTemplate.update("SELECT COUNT(*) FROM recommendations;");
        counterOfRecommendations++;
        return counterOfRecommendations;
    }
    
    public void createRecommendation(Recommendation recommendation) {
        List<DynamicRule> dynamic_rules = recommendation.getRule();
        for(DynamicRule dynamicRule : dynamic_rules) {
            dynamicRuleRepository.save(dynamicRule);
        }

        jdbcTemplate.update("INSERT INTO recommendations (productName, productId, productText, rule) VALUES {}, {}, {}, {}" +
                        " ARRAY (SELECT ROW(query, arguments, negate) FROM dynamic_rules WHERE recommendation_id = {})",
                recommendation.getProduct_name(),
                recommendation.getProduct_id() + "::UUID ",
                recommendation.getProduct_text(),
                dynamic_rules,
                counterOfRecommendations);

        logger.info("Recommendation \"{}\" was successfully created in database", recommendation.getProduct_name());
    }

    public String getRecommendation(String productType) {
        try {
            String result = jdbcTemplate.queryForObject(
                    "SELECT productName, productId, productText " +
                            "FROM recommendations " +
                            "WHERE productName = ?;",
                    String.class,
                    productType);
            logger.info("Result of method checking rule \"getRecommendation\" is {}", result);
            return result;
        } catch (
                Exception e) {
            logger.error("Error in \"getRecommendation\", message: {}", e.getMessage(), e);
            return "";
        }
    }


    /**
     * USER_OF
     * Пользователь использует как минимум один продукт с типом {productType}.
     * Данный запрос принимает только один аргумент - тип продукта:
     * DEBIT, CREDIT, INVEST, SAVING
     */
    public boolean isTheUserOfTheProduct (UUID userId, String productType){
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

/*            //следующий запрос проверяет, есть ли такое правило в таблице,
            // если нет, то добавляет созданное правило в таблицу
            arguments = List.of(productType);
            dynamicRule = new DynamicRule("USER_OF", arguments, true);

            try {
                Boolean dataBaseSearchResult = jdbcTemplate.queryForObject(
                        "SELECT * FROM dynamic_rules WHERE query = ?;",
                        Boolean.class, "USER_OF");
                logger.info("Database search result for this query {} is {}", dynamicRule.getQuery(), dataBaseSearchResult);
                if (!Boolean.TRUE.equals(dataBaseSearchResult)) {
                    dynamicRuleRepository.save(dynamicRule);
                }
            }catch (Exception ex) {
                logger.error("Error during saving an object {} to the database, message {}", DynamicRule.class, ex.getMessage(), ex);
                return false;
            }*/

            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"isTheUserOfTheProduct\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * ACTIVE_USER_OF
     * Пользователь использует как минимум один продукт с типом {productType}.
     * Данный запрос принимает только один аргумент - тип продукта:
     * DEBIT, CREDIT, INVEST, SAVING
     */
    public boolean isTheActiveUserOfTheProduct (UUID userId, String productType){
        try {
            Boolean result = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) > 5 " +
                            "FROM transactions t " +
                            "JOIN products p " +
                            "ON t.product_id = p.id " +
                            "WHERE t.user_id = ? AND p.type = ?;",
                    Boolean.class,
                    userId,
                    productType);
            logger.info("Result of method checking rule \"isTheActiveUserOfTheProduct\" with input parameters {}, {} is {}", userId, productType, result);

        /*    //следующий запрос проверяет, есть ли такое правило в таблице,
            // если нет, то добавляет созданное правило в таблицу
            arguments = List.of(productType);
            dynamicRule = new DynamicRule("ACTIVE_USER_OF", arguments, true);

            try {
                Boolean dataBaseSearchResult = jdbcTemplate.queryForObject(
                        "SELECT * FROM dynamic_rules WHERE query = ?;",
                        Boolean.class, "ACTIVE_USER_OF");
                logger.info("Database search result for query {} is {}", dynamicRule.getQuery(), dataBaseSearchResult);
                if (!Boolean.TRUE.equals(dataBaseSearchResult)) {
                    dynamicRuleRepository.save(dynamicRule);
                }
            }catch (Exception ex) {
                logger.error("Error during saving an object {} to the database, message {}", DynamicRule.class, ex.getMessage(), ex);
                return false;
            }*/

            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"isTheActiveUserOfTheProduct\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * TRANSACTION_SUM_COMPARE
     * Сумма {transactionType} по всем продуктам типа {productType} {operationType}, чем {String amountForComparing} ₽
     * Данный запрос принимает четыре аргумента:
     * Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING
     * Второй аргумент — тип транзакции: WITHDRAW, DEPOSIT
     * Третий аргумент — оператор сравнения
     * Четвертый аргумент — неотрицательное целое число int (больше или равно нулю), с которым сравниваем сумму в формате String
     */
    public boolean comparingTransactionAmounts(UUID userId, String transactionType, String productType, String operationType, String amountForComparing) {
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

          /*  //следующий запрос проверяет, есть ли такое правило в таблице,
            // если нет, то добавляет созданное правило в таблицу
            arguments = List.of(transactionType, productType, operationType, amountForComparing);
            dynamicRule = new DynamicRule("TRANSACTION_SUM_COMPARE", arguments, true);

            try {
                Boolean dataBaseSearchResult = jdbcTemplate.queryForObject(
                        "SELECT * FROM dynamic_rules WHERE query = ?;",
                        Boolean.class, "TRANSACTION_SUM_COMPARE");
                logger.info("Database search result for query {}: {}", dynamicRule.getQuery(), dataBaseSearchResult);
                if (!Boolean.TRUE.equals(dataBaseSearchResult)) {
                    dynamicRuleRepository.save(dynamicRule);
                }
            }catch (Exception ex) {
                    logger.error("Error during saving an object {} to the database, message {}", DynamicRule.class, ex.getMessage(), ex);
                    return false;
                }
*/
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"comparingTransactionAmounts\" for userId: {}, message: {}",
                    userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     *  TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
     *  Сравнение суммы пополнений с тратами по всем продуктам одного типа
     *  Данный запрос принимает два аргумента:
     *  Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING
     *  Второй аргумент — оператор сравнения
     */
    public boolean comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(UUID userId, String productType, String operationType) {
        try {
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
/*
            //следующий запрос проверяет, есть ли такое правило в таблице,
            // если нет, то добавляет созданное правило в таблицу

            arguments = List.of(productType, operationType);
            dynamicRule = new DynamicRule("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW", arguments, true);

            try {
                Boolean dataBaseSearchResult = jdbcTemplate.queryForObject(
                        "SELECT * FROM dynamic_rules WHERE query = ?;",
                        Boolean.class, "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
                logger.info("Database search result for this query {}: is {}.", dynamicRule.getQuery(), dataBaseSearchResult);
                if (!Boolean.TRUE.equals(dataBaseSearchResult)) {
                    dynamicRuleRepository.save(dynamicRule);
                }
            }catch (Exception ex) {
                logger.error("Error during saving an object {} to the database, message {}", DynamicRule.class, ex.getMessage(), ex);
                return false;
            }*/

            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"comparingTheAmountOfDepositsWithWithdrawsOfOneProductType\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }
}