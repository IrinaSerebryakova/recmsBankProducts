package com.project.command.service.interfaces;

import com.project.command.model.RecommendationsDTO;

import java.util.List;
import java.util.UUID;

public interface RecommendationsService {
    List<RecommendationsDTO> getRecommendations(UUID userId);
}
