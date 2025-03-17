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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class DynamicRuleRepository implements JpaRepository<DynamicRule, Long> {

    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(DynamicRuleRepository.class);

    public final static List<String> productType = List.of("DEBIT", "CREDIT", "INVEST", "SAVING");
    public final static List<String> requestType = List.of("USER_OF", "ACTIVE_USER_OF", "TRANSACTION_SUM_COMPARE",
            "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
    public final static List<String> transactionType = List.of("WITHDRAW", "DEPOSIT");
    public final static List<String> operationType = List.of(">", "<", "=", ">=", "<=");
    public static int amountForComparing;

    public DynamicRuleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DynamicRule save(DynamicRule dynamicRule) {
        return new DynamicRule();
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

    public List<DynamicRule> findAll() {
        List<DynamicRule> dynamicRuleList = new ArrayList<>();
        return dynamicRuleList;
    }

    @Override
    public List<DynamicRule> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    public void deleteById(Long dynamicRuleId) {
        Long result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1", //переписать для класса
                Long.class,
                dynamicRuleId);
        logger.info("Result of method \"deleteById\" with dynamicRuleId = {}  is {}", dynamicRuleId, result);
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
    public List<DynamicRule> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<DynamicRule> findAll(Pageable pageable) {
        return null;
    }
}

/**
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     *//*

public boolean addDebitMoreThanSpendDebit(UUID userId){
    try {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = 'DEBIT'``) > " +
                        "SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = 'DEBIT') " +
                        "THEN TRUE ELSE FALSE END " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?;",
                Boolean.class);
        logger.info("Result of method checking rule \"addDebitMoreThanSpendDebit\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"addDebitMoreThanSpendDebit\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}


*/
/**
 * Пользователь не использует продукты с типом INVEST.
 *//*

public boolean noOneProductInvest(UUID userId) {
    try {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) = 0 " +
                        "FROM transactions t " +
                        "JOIN products p " +
                        "ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = 'INVEST';",
                Boolean.class);
        logger.info("Result of method checking rule \"noOneProductInvest\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"noOneProductInvest\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}

*/
/**
 * Сумма пополнений продуктов с типом SAVING больше 1000 ₽.
 *//*

public boolean sumOfSavingMoreThanOneThousandRub(UUID userId) {
    try {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE " +
                        "WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'DEPOSIT' AND p.type = 'SAVING') > 1000" +
                        "THEN TRUE ELSE FALSE END " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?;",
                Boolean.class);
        logger.info("Result of method checking rule \"sumOfSavingMoreThanOneThousandRub\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"sumOfSavingMoreThanOneThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}

*/
/**
 * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
 *//*

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
                Boolean.class);
        logger.info("Result of method checking rule \"sumAddDebitOrSumAddSavingGreaterThanOrEqualToFiftyThousandRub\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"sumAddDebitOrSumAddSavingMoreThanFiftyThousandRub\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}


*/
/**
 * Пользователь не использует продукты с типом CREDIT.
 *//*

public boolean noOneProductCredit(UUID userId) {
    try {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) = 0 " +
                        "FROM transactions t " +
                        "JOIN products p " +
                        "ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = 'CREDIT';",
                Boolean.class);
        logger.info("Result of method checking rule \"noOneProductCredit\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"noOneProductCredit\" for userId: {}, message: {}", userId, e.getMessage(), e);
        return false;
    }
}

*/
/**
 * Пользователь использует как минимум один продукт с типом DEBIT.
 *//*

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

*/
/**
 * Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
 *//*

public boolean sumSpendDebitMoreOneHundredThousandsRub(UUID userId) {
    try {
        Boolean result = jdbcTemplate.queryForObject(
                "SELECT CASE " +
                        "WHEN SUM(amount::NUMERIC) FILTER (WHERE t.type = 'WITHDRAW' AND p.type = 'DEBIT') > 100000" +
                        "THEN TRUE ELSE FALSE END " +
                        "FROM transactions t " +
                        "JOIN products p ON t.product_id = p.id " +
                        "WHERE t.user_id = ?;",
                Boolean.class);
        logger.info("Result of method checking rule \"sumSpendDebitMoreOneHundredThousandsRub\" is {}", result);
        return result != null && result;
    } catch (Exception e) {
        logger.error("Error in \"sumSpendDebitMoreOneHundredThousandsRub\" for userId: {}, message: {}",
                userId, e.getMessage(), e);
        return false;
    }
}
*/
