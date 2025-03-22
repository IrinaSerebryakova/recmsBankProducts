package com.project.command.repository;

import com.project.command.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRecommendationRepository extends JpaRepository<Recommendation, Long> {
}
