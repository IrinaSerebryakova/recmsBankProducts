package com.project.command.controller;

import com.project.command.model.Query;
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
    public void createNewDynamicRule(@RequestBody Query query){
       dynamicRuleService.saveQuery(query);
    }

   @DeleteMapping("/{dynamicRuleId}")
    public void deleteDynamicRuleOfRecommendations(@PathVariable Long dynamicRuleId){
        dynamicRuleService.deleteDynamicRuleOfRecommendations(dynamicRuleId);
    }

    @GetMapping
    public List<Query> getAllQueries(){
        return dynamicRuleService.getAllQueries();
    }

    @GetMapping("/stats")
    public List<Statistics> getStatisticsOfDynamicRules(){
        return dynamicRuleService.getStatisticsOfDynamicRules();
    }
}
