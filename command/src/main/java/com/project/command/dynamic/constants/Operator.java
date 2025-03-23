package com.project.command.dynamic.constants;

public enum Operator {
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    EQUAL("="),
    LESS_THAN_OR_EQUAL("<="),
    LESS_THAN("<");

  private final String operator;
    Operator(String operator) {
        this.operator = operator;
    }
}

