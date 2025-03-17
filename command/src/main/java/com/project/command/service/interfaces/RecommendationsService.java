package com.project.command.service.interfaces;

import com.project.command.model.Recommendation;

import java.util.List;
import java.util.UUID;

public interface RecommendationsService {
    List<String> getRecommendations(UUID userId);
}
