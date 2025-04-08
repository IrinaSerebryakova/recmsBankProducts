package com.project.command.dynamic;

import com.project.command.dynamic.constants.Operator;

import java.util.Map;
import java.util.function.BiPredicate;

public class ComparisonOperation {
    private final Operator operator;

    private BiPredicate<Integer, Integer> serviceOfOperations(Operator operator) {
        return switch (operator) {
            case GREATER_THAN -> (w1, w2) -> w1 > w2;
            case LESS_THAN -> (w1, w2) -> w1 < w2;
            case EQUAL -> Integer::equals;
            case LESS_THAN_OR_EQUAL -> (w1, w2) -> w1 <= w2;
            case GREATER_THAN_OR_EQUAL -> (w1, w2) -> w1 >= w2;
            default -> null;
        };
    }

   public ComparisonOperation(Operator operator) {
        this.operator = operator;
    }

    public boolean compare(Operator  operator, int a, int b) {
       BiPredicate<Integer, Integer> serviceOfOperations = serviceOfOperations(operator);
       if(serviceOfOperations == null){
           throw new IllegalArgumentException("Unsupported operator: " + operator);
       }
        return serviceOfOperations.test(a, b);
    }
}
