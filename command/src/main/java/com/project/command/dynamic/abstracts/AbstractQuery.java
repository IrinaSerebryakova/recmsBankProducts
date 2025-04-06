package com.project.command.dynamic.abstracts;

import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractQuery {
    private final boolean negate;

    protected AbstractQuery(boolean negate) {
        this.negate = negate;
    }

    public boolean evaluate(UUID userId, RecommendationsRepository recommendationsRepository) {
        return negate != evaluateRequest(userId, recommendationsRepository);
    }

    protected abstract boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository);

    protected abstract String getQueryType();

    public abstract boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries);
}
