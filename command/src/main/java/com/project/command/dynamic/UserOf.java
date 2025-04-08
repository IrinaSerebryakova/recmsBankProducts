package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component("userOf")
public class UserOf extends AbstractQuery {
    private RecommendationsRepository recommendationsRepository;

    public UserOf() {
        super(false);
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public QueryType getQueryType() {
        return QueryType.USER_OF;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        if (query == null || query.getArguments() == null || query.getArguments().isEmpty()) {
            throw new IllegalArgumentException("Invalid query arguments");
        }
        String productType = query.getArguments().get(0);

        boolean result = recommendationsRepository.isTheUserOfTheProduct(
                id,
                productType
        );
        return query.isNegate() != result;
    }
}
