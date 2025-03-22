package com.project.command.dynamic;


import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;
import java.util.List;
import java.util.UUID;

public class TransactionSumCompareDepositWithdrow extends AbstractQuery {
    private final String productType;
    private final String operator; // TODO: ENUM

    protected TransactionSumCompareDepositWithdrow(List<String> args, boolean negate) {
        super(negate);
        productType = args.get(0);
        operator = args.get(1);
    }

    @Override
    public boolean evaluateInternal(UUID userId, RecommendationsRepository recommendationsRepository) {
        return false;
    }
}
