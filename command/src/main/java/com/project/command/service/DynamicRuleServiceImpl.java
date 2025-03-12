package com.project.command.service;

import com.project.command.model.DynamicRule;
import com.project.command.repository.DynamicRuleRepository;
import com.project.command.service.interfaces.DynamicRuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleServiceImpl implements DynamicRuleService {
    private final DynamicRuleRepository dynamicRuleRepository;

    public DynamicRuleServiceImpl(DynamicRuleRepository dynamicRuleRepository) {
        this.dynamicRuleRepository = dynamicRuleRepository;
    }
@Override
    public DynamicRule createNewDynamicRuleOfRecommendations(DynamicRule dynamicRule) {
        return dynamicRuleRepository.save(dynamicRule);
    }
    @Override
    public List<DynamicRule> getListOfDynamicRulesOfRecommendations(){
        return dynamicRuleRepository.findAll();
    }
    @Override
    public DynamicRule deleteDynamicRuleOfRecommendations(UUID dynamicRuleId) {
        return dynamicRuleRepository.deleteById(dynamicRuleId);
    }
}
