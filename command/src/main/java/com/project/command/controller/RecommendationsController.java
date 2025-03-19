package com.project.command.controller;

import com.project.command.model.DynamicRule;
import com.project.command.model.Recommendation;
import com.project.command.service.interfaces.RecommendationsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
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
    public List<String> getRecommendationByUserId(@PathVariable UUID userId) {
        return recommendationsServiceImpl.getRecommendations(userId);
    }

    @PostMapping("/create")
    public Recommendation createRecommendation(@Valid @NotNull @RequestBody Recommendation recommendation) {
        return recommendationsServiceImpl.createRecommendation(recommendation);
    }
}