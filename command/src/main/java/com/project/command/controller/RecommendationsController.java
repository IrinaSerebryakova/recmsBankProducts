package com.project.command.controller;

import com.project.command.model.Rule;
import com.project.command.service.RecommendationsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {

    private final RecommendationsService recommendationsServiceImpl;

    public RecommendationsController(RecommendationsService recommendationsServiceImpl) {
        this.recommendationsServiceImpl = recommendationsServiceImpl;
    }

    @GetMapping("/{userId}")
    public List<Rule> getRecommendationByUserId(@PathVariable UUID userId) {
        return recommendationsServiceImpl.getRecommendations(userId);
    }
}