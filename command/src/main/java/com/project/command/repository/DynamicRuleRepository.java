package com.project.command.repository;

import com.project.command.model.DynamicRule;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DynamicRuleRepository extends JpaRepository<DynamicRule, Long>{


}
