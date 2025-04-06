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

@Component
public class TransactionSumCompare extends AbstractQuery {
    private String queryType = TRANSACTION_SUM_COMPARE.name();
    private String productType;
    private String transactionType;
    private String operator;
    private String amountForComparing;
    private boolean negate;
    RecommendationsRepository recommendationsRepository;

    /**
     * Конструктор со значениями по умолчанию
     */
    public TransactionSumCompare() {
        super(false);
        this.transactionType = null;
        this.productType = null;
        this.operator = null;
        this.amountForComparing = null;
    }

    public void setArgs(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Argument list cannot be null or empty");
        }
        this.transactionType = args.get(0);
        this.productType = args.get(1);
        this.operator = args.get(2);
        this.amountForComparing = args.get(3);
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    @Override
    protected boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository) {
        return recommendationsRepository.comparingTransactionAmounts(userId, transactionType, productType, operator, amountForComparing);
    }

    @Override
    public String getQueryType() {
        return queryType;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        return query.isNegate() != recommendationsRepository.comparingTransactionAmounts(id, query.getArguments().get(0), query.getArguments().get(1), query.getArguments().get(2), query.getArguments().get(3));
    }
}


