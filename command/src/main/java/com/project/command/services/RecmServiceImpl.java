
package com.project.command.services;

import com.project.command.component.RecmRuleSetImplToCredit;
import com.project.command.component.RecmRuleSetImplToInvest;
import com.project.command.component.RecmRuleSetImplToSave;
import com.project.command.model.RecmDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecmServiceImpl implements RecmService {

    private final JdbcTemplate jdbcTemplate;

    public RecmServiceImpl(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Optional<RecmDTO>> getRecommendationByUserId(String user_id) {
        List<Optional<RecmDTO>> userRecommendations = new ArrayList<>(Collections.emptyList());

        RecmRuleSetImplToCredit recmRuleSetImplToCredit = new RecmRuleSetImplToCredit(jdbcTemplate);
                userRecommendations.add(recmRuleSetImplToCredit.evaluateRules(user_id));

                RecmRuleSetImplToSave recmRuleSetImplToSave = new RecmRuleSetImplToSave(jdbcTemplate);
                userRecommendations.add(recmRuleSetImplToSave.evaluateRules(user_id));

                RecmRuleSetImplToInvest recmRuleSetImplToInvest = new RecmRuleSetImplToInvest(jdbcTemplate);
                userRecommendations.add(recmRuleSetImplToInvest.evaluateRules(user_id));

        return userRecommendations;
    }
}

