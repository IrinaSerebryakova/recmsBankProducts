package com.project.command.service;

import com.project.command.dynamic.ActiveUserOf;
import com.project.command.dynamic.TransactionSumCompare;
import com.project.command.dynamic.TransactionSumCompareDepositWithdrow;
import com.project.command.dynamic.UserOf;
import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.model.Rule;
import com.project.command.model.Statistics;
import com.project.command.repository.RecommendationsRepository;
import com.project.command.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.project.command.dynamic.constants.QueryType.*;

@Service
public class DynamicRuleService {

    private final RuleRepository ruleRepository;
    private final ActiveUserOf activeUserOf;
    private final TransactionSumCompare transactionSumCompare;
    private final TransactionSumCompareDepositWithdrow transactionSumCompareDepositWithdrow;
    private final UserOf userOf;
    private final RecommendationsRepository recommendationsRepository;

    public DynamicRuleService(RuleRepository ruleRepository,
                              UserOf userOf,
                              ActiveUserOf activeUserOf,
                              TransactionSumCompare transactionSumCompare,
                              TransactionSumCompareDepositWithdrow transactionSumCompareDepositWithdrow,
                              RecommendationsRepository recommendationsRepository) {
        this.ruleRepository = ruleRepository;
        this.activeUserOf = activeUserOf;
        this.transactionSumCompare = transactionSumCompare;
        this.transactionSumCompareDepositWithdrow = transactionSumCompareDepositWithdrow;
        this.userOf = userOf;
        this.recommendationsRepository = recommendationsRepository;
    }

    public Rule saveRule(Rule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        return ruleRepository.save(rule);
    }

    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    public List<Rule> getListOfRulesForUser(UUID userId) {
        List<Rule> rules3 = ruleRepository.findAll();
        List<Rule> result = new ArrayList<>();

        for (Rule rule : rules3) {
            List<Query> queries = rule.getQueries();
            if (checkAllQueries(userId, queries)) {
                result.add(rule);
            }
        }
        return result;
    }

    private boolean checkAllQueries(UUID id, List<Query> queries) {
        for (Query query : queries) {
            if (!checkQuery(id, query)) {    // паттерн fail fast
                return false;
            }
        }
        return true;
    }

    /**
     * Создаем Map (key - тип запроса, value - обработчик запроса в соответствии с типом запроса)
     * Получаем у входящего query тип, вызываем соответствующий обработчик
     * @param id
     * @param query
     * @return
     */
    public boolean checkQuery(UUID id, Query query) {
        Map<QueryType, AbstractQuery> queries = new HashMap<>();
        queries.put(USER_OF, userOf);
        queries.put(ACTIVE_USER_OF, activeUserOf);
        queries.put(TRANSACTION_SUM_COMPARE, transactionSumCompare);
        queries.put(TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW, transactionSumCompareDepositWithdrow);
        AbstractQuery abstractQuery = queries.get(query.getQuery());
        return abstractQuery.handle(id, query, queries);
    }

    public void deleteDynamicRuleOfRecommendations(Long dynamicRuleId) {
        ruleRepository.deleteById(dynamicRuleId);
    }

    public List<Statistics> getStatisticsOfDynamicRules() {
        return new ArrayList<Statistics>();
    }
}
