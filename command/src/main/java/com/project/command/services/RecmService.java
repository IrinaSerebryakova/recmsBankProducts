package com.project.command.services;

import com.project.command.model.RecmDTO;

import java.util.List;
import java.util.Optional;

public interface RecmService {
    List<Optional<RecmDTO>> getRecommendationByUserId(String userId);
}
