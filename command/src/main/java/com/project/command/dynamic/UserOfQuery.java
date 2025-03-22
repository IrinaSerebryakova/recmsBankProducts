package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;

import java.util.List;
import java.util.UUID;

public class UserOfQuery extends AbstractQuery {
    private final String productType;

    public UserOfQuery(List<String> args,
                       boolean negate
    ) {
        super(negate);
        productType = args.get(0);
    }

    @Override
    public boolean evaluateInternal(UUID userId, RecommendationsRepository recommendationsRepository) {
        return recommendationsRepository.isTheUserOfTheProduct(userId, productType);
    }
}
