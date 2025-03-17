package com.project.command.repository;

import com.project.command.model.DynamicRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
public class RecommendationsRepository implements JpaRepository<DynamicRule, Long> {

    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(RecommendationsRepository.class);

    public RecommendationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID userId) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                userId);
        return result != null ? result : 0;
    }

    public Optional<String> findUserNameById(UUID userId) {
        String userName = jdbcTemplate.queryForObject(
                "SELECT username FROM users t WHERE t.user_id = ?;",
                String.class,
                userId);
        if (userName != null) {
            return Optional.of(userName);
        } else {
            return Optional.empty();
        }
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
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"isTheActiveUserOfTheProduct\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    /**
     * TRANSACTION_SUM_COMPARE
     * Сумма {transactionType} по всем продуктам типа {productType} {operationType}, чем {int amountForComparing} ₽
     * Данный запрос принимает четыре аргумента:
     * Первый аргумент — тип продукта: DEBIT, CREDIT, INVEST, SAVING
     * Второй аргумент — тип транзакции: WITHDRAW, DEPOSIT
     * Третий аргумент — оператор сравнения
     * Четвертый аргумент — неотрицательное целое число int (больше или равно нулю), с которым сравниваем сумму
     */
    public boolean comparingTransactionAmounts(UUID userId, String transactionType, String productType, String operationType, int amountForComparing) {
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
            return result != null && result;
        } catch (Exception e) {
            logger.error("Error in \"comparingTheAmountOfDepositsWithWithdrawsOfOneProductType\" for userId: {}, message: {}", userId, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends DynamicRule> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends DynamicRule> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<DynamicRule> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public DynamicRule getOne(Long aLong) {
        return null;
    }

    @Override
    public DynamicRule getById(Long aLong) {
        return null;
    }

    @Override
    public DynamicRule getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends DynamicRule> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends DynamicRule> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends DynamicRule> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends DynamicRule> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends DynamicRule> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends DynamicRule> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends DynamicRule, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends DynamicRule> S save(S entity) {
        return null;
    }

    @Override
    public <S extends DynamicRule> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<DynamicRule> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<DynamicRule> findAll() {
        return List.of();
    }

    @Override
    public List<DynamicRule> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(DynamicRule entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends DynamicRule> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<DynamicRule> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<DynamicRule> findAll(Pageable pageable) {
        return null;
    }
}