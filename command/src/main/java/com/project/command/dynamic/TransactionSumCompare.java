package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionSumCompare extends AbstractQuery {
    private String productType;
    private String transactionType;
    private String operator;
    private String amountForComparing;
    private boolean negate;

    public TransactionSumCompare() {
        super(false); // Значение по умолчанию для negate
        this.transactionType = null; // Инициализация по умолчанию
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
}


