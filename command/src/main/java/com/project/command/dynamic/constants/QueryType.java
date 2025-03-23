package com.project.command.dynamic.constants;

public enum QueryType {
    USER_OF("Является пользователем продукта"),
    ACTIVE_USER_OF("Является активным пользователем продукта"),
    TRANSACTION_SUM_COMPARE("Сравнение суммы транзакций с константой"),
    TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW("Сравнение суммы пополнений с тратами по всем продуктам одного типа");

    private final String queryType;

    QueryType(String queryType) {
        this.queryType = queryType;
    }
}