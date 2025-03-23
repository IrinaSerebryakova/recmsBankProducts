package com.project.command.dynamic;

import java.util.Map;
import java.util.function.BiPredicate;

public class ComparisonOperation {
    private static final Map<String, BiPredicate<Integer, Integer>> MAP = Map.of(
            ">", (w1, w2) -> w1 > w2,
            "<", (w1, w2) -> w1 < w2,
            "=", Integer::equals,
            "<=", (w1, w2) -> w1 <= w2,
            ">=", (w1, w2) -> w1 >= w2
    );

    private final String operator;

    public ComparisonOperation(String operator) {
        this.operator = operator;
    }

    public boolean compare(int a, int b) {
        return MAP.get(operator).test(a, b);
    }
}
