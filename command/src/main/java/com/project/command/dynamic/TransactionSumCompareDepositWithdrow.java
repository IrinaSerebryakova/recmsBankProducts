package com.project.command.dynamic;


import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Component("transactionSumCompareDepositWithdrow")
public class TransactionSumCompareDepositWithdrow extends AbstractQuery {
    private RecommendationsRepository recommendationsRepository;

    public TransactionSumCompareDepositWithdrow() {
        super(false);
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        if (query == null || query.getArguments() == null || query.getArguments().size() < 2) {
            throw new IllegalArgumentException("Invalid query arguments");
        }
        String productType = query.getArguments().get(0);
        String operator = query.getArguments().get(1);

        boolean result = recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(
                id,
                productType,
                operator
        );
        return query.isNegate() != result;
    }
}
