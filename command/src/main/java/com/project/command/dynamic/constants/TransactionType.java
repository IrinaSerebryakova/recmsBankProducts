package com.project.command.dynamic.constants;

public enum TransactionType {
    WITHDRAW("WITHDRAW"),
    DEPOSIT("DEPOSIT");

    private final String transactionType;

    TransactionType( String transactionType) {
        this.transactionType = transactionType;
    }
}
