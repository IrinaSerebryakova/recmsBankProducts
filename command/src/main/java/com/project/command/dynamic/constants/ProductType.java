package com.project.command.dynamic.constants;

public enum ProductType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    INVEST("INVEST"),
    SAVING("SAVING");

    private final String productType;

    ProductType(String productType) {
        this.productType = productType;
    }
}
