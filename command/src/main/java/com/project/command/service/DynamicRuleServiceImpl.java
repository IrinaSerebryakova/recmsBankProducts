package com.project.command.service;

import com.project.command.repository.DynamicRuleRepository;
import com.project.command.model.DynamicRule;
import com.project.command.service.interfaces.DynamicRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicRuleServiceImpl implements DynamicRuleService {
    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleServiceImpl(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }

    public List<DynamicRule> createNewDynamicRuleOfRecommendations() {
        return dynamicRuleRepository.save(new DynamicRule());
    }

    public List<DynamicRule> getListOfDynamicRulesOfRecommendations(){
        return dynamicRuleRepository.findAll();
    }

    public List<DynamicRule> deleteDynamicRuleOfRecommendations(Long productId) {
        return dynamicRuleRepository.deleteById(productId);
    }
}
