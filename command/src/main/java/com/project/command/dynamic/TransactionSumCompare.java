package com.project.command.dynamic;

import com.project.command.dynamic.constants.Operator;
import com.project.command.dynamic.constants.ProductType;
import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;
import java.util.List;
import java.util.UUID;

public class TransactionSumCompare extends AbstractQuery {
    private final ProductType productType;
    private final Operator operator;

    protected TransactionSumCompare(List<String> args, boolean negate) {
        super(negate);
        this.productType = ProductType.valueOf(args.get(0));
        this.operator = Operator.valueOf(args.get(1));
    }

    @Override
    protected boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository) {
        return recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, productType, String.valueOf(operator));
    }
}


