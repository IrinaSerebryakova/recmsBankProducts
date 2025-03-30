package com.project.command.controller;

import com.project.command.service.RecommendationsService;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final Optional<BuildProperties> buildProperties;
    private final RecommendationsService recommendationsService;

    public ManagementController(Optional<BuildProperties> buildProperties, RecommendationsService recommendationsService) {
        this.buildProperties = buildProperties;
        this.recommendationsService = recommendationsService;
    }

    @GetMapping("/info")
    public Map<String, String> getInfo() {
        Map<String, String> info = new HashMap<>();
        buildProperties.ifPresent(buildProperties -> {
            info.put("name", buildProperties.getName());
            info.put("version", buildProperties.getVersion());
        });
        return info;
    }

    @PostMapping("/clear-caches")
    public void clearCaches() {
        recommendationsService.clearCashes();
    }
}
