package com.project.command.dynamic.abstracts;

import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractQuery {
    private RecommendationsRepository recommendationsRepository;
    private boolean negate = false;

    public AbstractQuery(boolean negate) {
        this.negate = negate;
    }

    public abstract QueryType getQueryType();

    public abstract boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries);
}
