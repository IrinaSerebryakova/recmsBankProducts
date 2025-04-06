package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.dynamic.constants.QueryType;
import com.project.command.model.Query;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.project.command.dynamic.constants.QueryType.ACTIVE_USER_OF;

@Component
public class ActiveUserOf extends AbstractQuery {
    private String queryType = ACTIVE_USER_OF.name();
    private String productType;
    private boolean negate;
    RecommendationsRepository recommendationsRepository;

    /**
    * Конструктор со значениями по умолчанию
    */
    public ActiveUserOf() {
        super(false);
        this.productType = null;
    }

    public void setArgs(List<String> args) {
        if (args == null || args.isEmpty()) {
            throw new IllegalArgumentException("Argument list cannot be null or empty");
        }
        this.productType = args.get(0);
    }

    public void setNegate(boolean negate) {
        this.negate = negate;
    }

    @Override
    protected boolean evaluateRequest(UUID userId, RecommendationsRepository recommendationsRepository) {
        return recommendationsRepository.isTheActiveUserOfTheProduct(userId, productType);
    }

    @Override
    public String getQueryType() {
        return queryType;
    }

    public boolean handle(UUID id, Query query, Map<QueryType, AbstractQuery> queries) {
        return query.isNegate() != recommendationsRepository.isTheActiveUserOfTheProduct(id, query.getArguments().get(0));
    }
}
