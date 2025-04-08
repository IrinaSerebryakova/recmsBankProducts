package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component("transactionSumCompare")
public class TransactionSumCompare extends AbstractQuery {
    private RecommendationsRepository recommendationsRepository;

    public TransactionSumCompare(RecommendationsRepository recommendationsRepository) {
        super(false);
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.TRANSACTION_SUM_COMPARE;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        if (query == null || query.getArguments() == null || query.getArguments().size() < 4) {
            throw new IllegalArgumentException("Invalid query arguments");
        }
        String transactionType = query.getArguments().get(0);
        String productType = query.getArguments().get(1);
        String operator = query.getArguments().get(2);
        String amountForComparing = query.getArguments().get(3);

        boolean result = recommendationsRepository.comparingTransactionAmounts(
                id,
                transactionType,
                productType,
                operator,
                amountForComparing
        );
        return query.isNegate() != result;
    }
}


