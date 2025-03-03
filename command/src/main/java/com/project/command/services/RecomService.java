package com.project.command.services;

import com.project.command.model.RecomDTO;

import java.util.List;

public interface RecomService {
    List<RecomDTO> getRecommendations(String userId);

}
