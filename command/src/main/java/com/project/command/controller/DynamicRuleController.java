package com.project.command.controller;

import com.project.command.model.DynamicRule;
import com.project.command.service.interfaces.DynamicRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleServiceImpl;

    public DynamicRuleController(DynamicRuleService dynamicRuleServiceImpl) {
        this.dynamicRuleServiceImpl = dynamicRuleServiceImpl;
    }

    @PostMapping
    public DynamicRule createNewDynamicRuleOfRecommendations(@PathVariable DynamicRule dynamicRule){
        return dynamicRuleServiceImpl.createNewDynamicRuleOfRecommendations(dynamicRule);
    }

    @GetMapping
    public List<DynamicRule> getListOfDynamicRulesOfRecommendations(){
        return dynamicRuleServiceImpl.getListOfDynamicRulesOfRecommendations();
    }

    @DeleteMapping("/{dynamicRuleId}")
    public DynamicRule deleteDynamicRuleOfRecommendations(@PathVariable UUID dynamicRuleId){
        return dynamicRuleServiceImpl.deleteDynamicRuleOfRecommendations(dynamicRuleId);
    }

}
