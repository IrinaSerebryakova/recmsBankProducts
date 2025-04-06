package com.project.command.dynamic;


import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.project.command.dynamic.constants.QueryType.TRANSACTION_SUM_COMPARE;
import static com.project.command.dynamic.constants.QueryType.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW;

@Component
public class TransactionSumCompareDepositWithdrow extends AbstractQuery {
    private String queryType = TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW.name();
    private String productType;
    private String operator;
    private boolean negate;
    RecommendationsRepository recommendationsRepository;

    /**
     * Конструктор со значениями по умолчанию
     */
    public TransactionSumCompareDepositWithdrow() {
        super(false);
        this.productType = null;
        this.operator = null;
    }

    public void setArgs(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Argument list cannot be null or empty");
        }
        this.productType = args.get(0);
        this.operator = args.get(1);
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    @Override
    protected boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository) {
        return recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, productType, operator);
    }

    @Override
    public String getQueryType() {
        return queryType;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        return query.isNegate() != recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(id, query.getArguments().get(0), query.getArguments().get(1));
    }
}
