package com.project.command.service;

import com.project.command.model.Statistics;
import com.project.command.repository.StatisticsRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StatisticsService {

    private StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }


    /**
     * Получение списка статистики из базы данных.
     * @return список объектов Statistics
     */
    public List<Statistics> getStatisticsOfDynamicRules() {
        return statisticsRepository.getStatisticsOfDynamicRules();
    }


    /**
     * RowMapper для преобразования ResultSet в объект Statistics.
     */
    private static class StatisticsRowMapper implements RowMapper<Statistics> {
        @Override
        public Statistics mapRow(ResultSet rs, int rowNum) throws SQLException {
            Statistics statistics = new Statistics();
            statistics.setRuleId(rs.getLong("rule_id"));
            statistics.setCount(rs.getInt("count"));
            return statistics;
        }
    }
}
