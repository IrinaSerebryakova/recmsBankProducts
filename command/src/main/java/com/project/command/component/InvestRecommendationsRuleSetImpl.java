package com.project.command.component;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.dynamic.constants.Operator;
import com.project.command.dynamic.constants.ProductType;
import com.project.command.dynamic.constants.TransactionType;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.project.command.dynamic.RecommendationsConstants.INVEST_RECOMMENDATIONS;

@Component
public class InvestRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(InvestRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public InvestRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Проверяет выполнение требований:
     * Пользователь использует как минимум один продукт с типом DEBIT.
     * Пользователь не использует продукты с типом INVEST.
     * Сумма пополнений продуктов с типом SAVING больше 1000 ₽
     */
    public Optional<Rule> evaluateRules(UUID userId) {
        boolean evaluate = recommendationsRepository.isTheUserOfTheProduct(userId, ProductType.DEBIT) &&
                !recommendationsRepository.isTheUserOfTheProduct(userId, ProductType.INVEST) &&
                recommendationsRepository.comparingTransactionAmounts(userId, String.valueOf(TransactionType.DEPOSIT), String.valueOf(ProductType.SAVING), String.valueOf(Operator.GREATER_THAN), "1000");
        return Optional.ofNullable(evaluate ? INVEST_RECOMMENDATIONS : null);
    }
}


