package com.project.command.repository;

import com.project.command.model.Rule;
import com.project.command.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Rule, Long> {

    @Query(value = "SELECT * FROM stats", nativeQuery = true)
    List<Statistics> getStatisticsOfDynamicRules();

}
