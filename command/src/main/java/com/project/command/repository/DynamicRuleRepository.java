package com.project.command.repository;

import com.project.command.model.DynamicRule;
import com.project.command.model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DynamicRuleRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(DynamicRuleRepository.class);
    private RecommendationsRepository recommendationsRepository;

    public DynamicRuleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(DynamicRule dynamicRule) {
        jdbcTemplate.update("INSERT INTO dynamic_rules VALUES {}, {}, {}, {}", dynamicRule.getId(), dynamicRule.getQuery(), dynamicRule.getArguments(), true, recommendationsRepository.getCounterOfRecommendations());
        logger.info("Dynamic rule \"{}\" was successfully created in database", dynamicRule.getQuery());
    }

    public Optional<DynamicRule> findById(Long dynamicRuleId) {
        Optional<DynamicRule> dynamicRule = Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT query, arguments, negate " +
                        "FROM dynamic_rules WHERE id = ?", DynamicRule.class, dynamicRuleId));
        logger.info("The result of the dynamic rules search by id in the database: {}", dynamicRule);
        return dynamicRule;
    }

    public void deleteById(Long dynamicRuleId) {
        DynamicRule dynamicRule = jdbcTemplate.queryForObject(
                "SELECT query, arguments, negate " +
                        "FROM dynamic_rules WHERE id = ?", DynamicRule.class, dynamicRuleId);

        jdbcTemplate.update("DELETE FROM dynamic_rules WHERE id = {}", dynamicRuleId);
        logger.info("Dynamic rule \"{}\" was successfully deleted from database", dynamicRule.getQuery());
    }

    public List<DynamicRule> findAll() {
        List<DynamicRule> dynamicRules = jdbcTemplate.queryForList("SELECT * FROM dynamic_rules;", DynamicRule.class);
        logger.info("The result of the dynamic rules search in the database: {}", dynamicRules);
        return dynamicRules;
    }

    public List<Statistics> getStatistics() {
        List<Statistics> stats = jdbcTemplate.queryForList("SELECT * FROM stats;", Statistics.class);
        logger.info("The result of the method \"getStatistics\": {}", stats);
        return stats;
    }
}
