package com.project.command.service;

import com.project.command.dynamic.ActiveUserOf;
import com.project.command.dynamic.TransactionSumCompare;
import com.project.command.dynamic.TransactionSumCompareDepositWithdrow;
import com.project.command.dynamic.UserOf;
import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import com.project.command.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DynamicRuleService {

    private Map<QueryType, AbstractQuery> queriesMap;
    private RuleRepository ruleRepository;
    private ActiveUserOf activeUserOf;
    private TransactionSumCompare transactionSumCompare;
    private TransactionSumCompareDepositWithdrow transactionSumCompareDepositWithdrow;
    private UserOf userOf;
    private RecommendationsRepository recommendationsRepository;
    private final List<AbstractQuery> queries;

    public DynamicRuleService(RuleRepository ruleRepository,
                              UserOf userOf,
                              ActiveUserOf activeUserOf,
                              TransactionSumCompare transactionSumCompare,
                              TransactionSumCompareDepositWithdrow transactionSumCompareDepositWithdrow,
                              RecommendationsRepository recommendationsRepository,
                              List<AbstractQuery> queries) {
        this.ruleRepository = ruleRepository;
        this.activeUserOf = activeUserOf;
        this.transactionSumCompare = transactionSumCompare;
        this.transactionSumCompareDepositWithdrow = transactionSumCompareDepositWithdrow;
        this.userOf = userOf;
        this.recommendationsRepository = recommendationsRepository;
        this.queries = queries;
        this.queriesMap = queries.stream()
                .collect(Collectors.toMap(
                        AbstractQuery::getQueryType,
                        query -> query));
    }

    public Rule saveRule(Rule rule) {
        if (rule == null) {
            throw new IllegalArgumentException("Query cannot be null");
        }
        return ruleRepository.save(rule);
    }

    public List<Rule> getAllRules() {
        List<Rule> copyOfListOfRules = new ArrayList<>(ruleRepository.findAll());
        return copyOfListOfRules;
    }

    public List<Rule> getListOfRulesForUser(UUID userId) {
        List<Rule> rulesAll = ruleRepository.findAll();
        List<Rule> result = new ArrayList<>();

        for (Rule rule : rulesAll) {
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
        AbstractQuery abstractQuery = queriesMap.get(query.getQuery());
        return abstractQuery.handle(id, query, queriesMap);
    }

    public void deleteDynamicRuleOfRecommendations(Long dynamicRuleId) {
        ruleRepository.deleteById(dynamicRuleId);
    }
}
