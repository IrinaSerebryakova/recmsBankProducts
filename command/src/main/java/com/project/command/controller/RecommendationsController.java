package com.project.command.controller;

import com.project.command.service.interfaces.RecommendationsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RecommendationsController {

    private final RecommendationsService recommendationsServiceImpl;

    public RecommendationsController(RecommendationsService recommendationsServiceImpl) {
        this.recommendationsServiceImpl = recommendationsServiceImpl;
    }

    @GetMapping("/recommendation/{userId}")
    public List<String> getRecommendationByUserId(@PathVariable UUID userId) {
        return recommendationsServiceImpl.getRecommendations(userId);
    }
}