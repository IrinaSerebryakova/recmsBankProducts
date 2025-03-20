package com.project.command.service.interfaces;

import com.project.command.model.Recommendation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RecommendationsService {
    List<String> getRecommendations(UUID userId);

    Recommendation createRecommendation(Recommendation recommendation);
}
