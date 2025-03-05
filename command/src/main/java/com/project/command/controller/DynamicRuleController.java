package com.project.command.controller;

import com.project.command.model.DynamicRule;
import com.project.command.service.interfaces.DynamicRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleServiceImpl;

    public DynamicRuleController(DynamicRuleService dynamicRuleServiceImpl) {
        this.dynamicRuleServiceImpl = dynamicRuleServiceImpl;
    }

    @PostMapping("/rule")
    public List<DynamicRule> createNewDynamicRuleOfRecommendations(){
        return dynamicRuleServiceImpl.createNewDynamicRuleOfRecommendations();
    }

    @GetMapping("/rule")
    public List<DynamicRule> getListOfDynamicRulesOfRecommendations(){
        return dynamicRuleServiceImpl.getListOfDynamicRulesOfRecommendations();
    }

    @DeleteMapping("/rule/{productId}")
    public ResponseEntity<List<DynamicRule>> deleteDynamicRuleOfRecommendations(@PathVariable UUID productId){
        return dynamicRuleServiceImpl.deleteDynamicRuleOfRecommendations(productId);
    }

}
