package com.project.command.controller;

import com.project.command.model.Rule;
import com.project.command.model.Statistics;
import com.project.command.service.DynamicRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleServiceImpl) {
        this.dynamicRuleService = dynamicRuleServiceImpl;
    }

    @PostMapping
    public void createNewDynamicRule(@RequestBody Rule rule) {
       dynamicRuleService.saveRule(rule);
    }

   @DeleteMapping("/{dynamicRuleId}")
    public void deleteDynamicRuleOfRecommendations(@PathVariable Long dynamicRuleId){
        dynamicRuleService.deleteDynamicRuleOfRecommendations(dynamicRuleId);
    }

    @GetMapping
    public List<Rule> getAllRules(){
        return dynamicRuleService.getAllRules();
    }

    @GetMapping("/stats")
    public List<Statistics> getStatisticsOfDynamicRules(){
        return dynamicRuleService.getStatisticsOfDynamicRules();
    }
}
