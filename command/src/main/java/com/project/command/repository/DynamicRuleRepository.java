package com.project.command.repository;

import com.project.command.model.DynamicRule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long>{
    DynamicRule deleteById(UUID productId);
}
