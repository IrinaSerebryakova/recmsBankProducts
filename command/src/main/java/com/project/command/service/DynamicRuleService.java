package com.project.command.service;
import com.project.command.dynamic.UserOf;
import com.project.command.dynamic.ActiveUserOf;
import com.project.command.dynamic.TransactionSumCompare;
import com.project.command.dynamic.TransactionSumCompareDepositWithdrow;
import com.project.command.dynamic.constants.ProductType;
import com.project.command.model.Query;
import com.project.command.model.Rule;
import com.project.command.model.Statistics;
import com.project.command.repository.RecommendationsRepository;
import com.project.command.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService{

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

        for(Rule rule : rules3) {
            List<Query> queries = rule.getQueries();
            if (checkAllQueries(userId, queries)) {
                result.add(rule);
            }
        }
        return result;
    }

    private boolean checkAllQueries(UUID id, List<Query> queries){
        for(Query query : queries) {
            if(!checkQuery(id, query)) {    // паттерн fail fast
                return false;
            }
        }
        return true;
    }

    private boolean checkQuery(UUID id, Query query){
        return switch (query.getQuery()) {
            case USER_OF ->
                    query.isNegate() != recommendationsRepository.isTheUserOfTheProduct(id, query.getArguments().get(0));
            case ACTIVE_USER_OF ->
                    query.isNegate() != recommendationsRepository.isTheActiveUserOfTheProduct(id, query.getArguments().get(0));
            case TRANSACTION_SUM_COMPARE ->
                    query.isNegate() != recommendationsRepository.comparingTransactionAmounts(id, query.getArguments().get(0), query.getArguments().get(1), query.getArguments().get(2), query.getArguments().get(3));
            case TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW ->
                    query.isNegate() != recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(id, query.getArguments().get(0), query.getArguments().get(1));

            // бины передают состояние, но не должны, бины запоминают состояние.
//                д.б. бины кот ответят на вопрос кто они , getQueryType в них д.б. метод, тогда смогу собрать их в лист как рек.рулсет
//                    трансф в мэп
//                    query type - abstractQuery
//
//                switch будет не нужен
//                    беру у квери тип, достю нужный обработчик
//                    в идеале отдаю ему параметры и на выходе тру или фолс без сетаргс и тд
//                название поменять qwuery checkUserOf      - передавать всю квери туда

        };
    }

    public void deleteDynamicRuleOfRecommendations(Long dynamicRuleId) {
        ruleRepository.deleteById(dynamicRuleId);
    }

    public List<Statistics> getStatisticsOfDynamicRules() {
        return new ArrayList<Statistics>();
    }
}
