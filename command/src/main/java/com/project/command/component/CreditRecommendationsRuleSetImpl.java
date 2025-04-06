package com.project.command.component;

import com.project.command.component.interfaces.RecommendationsRuleSet;
import com.project.command.model.Rule;
import com.project.command.repository.RecommendationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.project.command.dynamic.RecommendationsConstants.CREDIT_RECOMMENDATIONS;
import static com.project.command.dynamic.constants.Operator.GREATER_THAN;
import static com.project.command.dynamic.constants.ProductType.DEBIT;
import static com.project.command.dynamic.constants.TransactionType.WITHDRAW;

@Component
public class CreditRecommendationsRuleSetImpl implements RecommendationsRuleSet {
    private final static String CREDIT = "Простой кредит";
    private final static Logger logger = LoggerFactory.getLogger(CreditRecommendationsRuleSetImpl.class);

    @Autowired
    private final RecommendationsRepository recommendationsRepository;

    @Autowired
    public CreditRecommendationsRuleSetImpl(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Проверяет выполнение требований:
     * Пользователь не использует продукты с типом CREDIT.
     * Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
     * Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
     * @param userId
     */
    public Optional<Rule> evaluateRules(UUID userId) {
        boolean evaluate =
                !recommendationsRepository.isTheUserOfTheProduct(userId, CREDIT) &&
                        (recommendationsRepository.comparingTheAmountOfDepositsWithWithdrawsOfOneProductType(userId, DEBIT.name(), GREATER_THAN.name())) &&
                        recommendationsRepository.comparingTransactionAmounts(userId, WITHDRAW.name(), DEBIT.name(), GREATER_THAN.name(), "100000");
        return Optional.ofNullable(evaluate ? CREDIT_RECOMMENDATIONS : null);
    }
}
