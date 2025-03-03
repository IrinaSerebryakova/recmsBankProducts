package com.project.command.controllers;

import com.project.command.model.RecomDTO;
import com.project.command.services.RecomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class RecomController {
    private final RecomService recomServiceImpl;

    public RecomController(RecomService recomServiceImpl) {
        this.recomServiceImpl = recomServiceImpl;
    }

    @GetMapping("/recommendation/{user_id}")
    public List<RecomDTO> getRecommendationByUserId(@PathVariable UUID user_id) {
        return recomServiceImpl.getRecommendations(user_id);
    }
}