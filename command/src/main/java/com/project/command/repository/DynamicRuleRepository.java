package com.project.command.repository;

import com.project.command.model.DynamicRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DynamicRuleRepository {
    private final JdbcTemplate jdbcTemplate;
    private final static Logger logger = LoggerFactory.getLogger(DynamicRuleRepository.class);

    public DynamicRuleRepository(@Qualifier("dynamicRuleJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DynamicRule save(DynamicRule dynamicRule) {
        return new DynamicRule();
    }

    public List<DynamicRule> findAll() {
        List<DynamicRule> dynamicRuleList = new ArrayList<>();
        return dynamicRuleList;
    }

    public DynamicRule deleteById(UUID dynamicRuleId) {
        Integer result = jdbcTemplate.queryForObject(
           "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1", //переписать для класса
                Integer.class,
                dynamicRuleId);
        return new DynamicRule();
    }

}
