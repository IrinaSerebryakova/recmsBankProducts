package com.project.command.dynamic;


import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class TransactionSumCompareDepositWithdrow extends AbstractQuery {
    private String productType;
    private String operator;
    private boolean negate;

    public TransactionSumCompareDepositWithdrow() {
        super(false); // Значение по умолчанию для negate
        this.productType = null; // Инициализация по умолчанию
        this.operator = null;
    }

 /*   public TransactionSumCompareDepositWithdrow(List<String> args, boolean negate) {
        super(negate);
        this.productType = args.get(0);
        this.operator = args.get(1);
    }*/

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
}
