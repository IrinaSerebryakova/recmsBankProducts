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

import static com.project.command.dynamic.RecommendationsConstants.SAVING_RECOMMENDATIONS;

@Component
public class SavingRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static Logger logger = LoggerFactory.getLogger(SavingRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public SavingRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Проверяет выполнение требований:
     * Пользователь использует как минимум один продукт с типом DEBIT.
     * Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     */
    public Optional<Rule> evaluateRules(UUID userId) {
        boolean evaluate = recommendationsRepository.isTheUserOfTheProduct(userId, ProductType.DEBIT) &&
                (recommendationsRepository.comparingTransactionAmounts(userId, String.valueOf(TransactionType.DEPOSIT), String.valueOf(ProductType.DEBIT), String.valueOf(Operator.GREATER_THAN), "50000") ||
                        recommendationsRepository.comparingTransactionAmounts(userId, String.valueOf(TransactionType.DEPOSIT), String.valueOf(ProductType.SAVING), String.valueOf(Operator.GREATER_THAN), "50000")) &&
                recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, ProductType.DEBIT, String.valueOf(Operator.GREATER_THAN));

        return Optional.ofNullable(evaluate ? SAVING_RECOMMENDATIONS : null);
    }
}
