package com.project.command.dynamic.abstracts;

import com.project.command.repository.RecommendationsRepository;
import java.util.UUID;

public abstract class AbstractQuery {
    private final boolean negate;

    protected AbstractQuery(boolean negate) {
        this.negate = negate;
    }

    public boolean evaluate(UUID userId, RecommendationsRepository recommendationsRepository){
        return negate != evaluateRequest(userId, recommendationsRepository);
    }

    protected abstract boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository);
}
