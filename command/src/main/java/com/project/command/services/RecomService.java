package com.project.command.services;

import com.project.command.model.RecomDTO;

import java.util.List;
import java.util.UUID;

public interface RecomService {
    List<RecomDTO> getRecommendations(UUID userId);

}
