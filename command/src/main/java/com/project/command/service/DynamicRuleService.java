package com.project.command.service;

import com.project.command.model.Query;
import com.project.command.model.Statistics;
import com.project.command.repository.DynamicRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicRuleService{

    @Autowired
    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public Query saveQuery(Query query) {
        if (query == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        return dynamicRuleRepository.save(query);
    }

    public List<Query> getAllQueries() {
        return dynamicRuleRepository.findAll();
    }

    public void deleteDynamicRuleOfRecommendations(Long dynamicRuleId) {
        dynamicRuleRepository.deleteById(dynamicRuleId);
    }

    public List<Statistics> getStatisticsOfDynamicRules() {
        return new ArrayList<Statistics>();
    }
}
