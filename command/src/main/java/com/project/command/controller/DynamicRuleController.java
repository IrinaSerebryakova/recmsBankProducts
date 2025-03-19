package com.project.command.controller;

import com.project.command.model.DynamicRule;
import com.project.command.service.interfaces.DynamicRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleServiceImpl;

    public DynamicRuleController(DynamicRuleService dynamicRuleServiceImpl) {
        this.dynamicRuleServiceImpl = dynamicRuleServiceImpl;
    }

    @PostMapping
    public void createNewDynamicRuleOfRecommendations(@PathVariable DynamicRule dynamicRule){
       dynamicRuleServiceImpl.createNewDynamicRuleOfRecommendations(dynamicRule);
    }

    @DeleteMapping("/{dynamicRuleId}")
    public Optional<DynamicRule> deleteDynamicRuleOfRecommendations(@PathVariable Long dynamicRuleId){
        return dynamicRuleServiceImpl.deleteDynamicRuleOfRecommendations(dynamicRuleId);
    }

    @GetMapping
    public List<DynamicRule> getListOfDynamicRulesOfRecommendations(){
        return dynamicRuleServiceImpl.getListOfDynamicRulesOfRecommendations();
    }
}
