package com.project.command.dynamic.abstracts;

import com.project.command.repository.RecommendationsRepository;
import java.util.UUID;

public abstract class AbstractQuery {
    private final boolean negate;

    protected AbstractQuery(boolean negate) {
        this.negate = negate;
    }

    public boolean evaluate(UUID userId, RecommendationsRepository recommendationsRepository){
        return negate != evaluateInternal(userId, recommendationsRepository);
    }

    public abstract boolean evaluateInternal(UUID userId, RecommendationsRepository recommendationsRepository);
}
