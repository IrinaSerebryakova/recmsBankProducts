package com.project.command.dynamic;

import com.project.command.dynamic.abstracts.AbstractQuery;
import com.project.command.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ActiveUserOf extends AbstractQuery {
    private String productType;
    private boolean negate;

    public ActiveUserOf() {
        super(false); // Значение по умолчанию для negate
        this.productType = null; // Инициализация по умолчанию
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
}
