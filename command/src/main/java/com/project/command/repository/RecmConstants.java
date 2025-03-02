package com.project.command.repository;

import com.project.command.model.RecmDTO;

import java.util.List;

public class RecmConstants {

    public final static String PRODUCT_NAME_INVEST = "Invest 500";
    public final static String PRODUCT_ID_INVEST = "147f6a0f-3b91-413b-ab99-87f081d60d5a";
    public final static String PRODUCT_TEXT_INVEST = """
            Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! \
            Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца \
            года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите \
            возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными \
            тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!""";

    public final static String PRODUCT_NAME_SAVING = "Top Saving";
    public final static String PRODUCT_ID_SAVING = "59efc529-2fff-41af-baff-90ccd7402925";
    public final static String PRODUCT_TEXT_SAVING = """
            Откройте свою собственную «Копилку» с нашим банком!
            «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать \
            деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
            Преимущества «Копилки»:
            Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет \
            автоматически переводить определенную сумму на ваш счет.
            Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления \
            и корректируйте стратегию при необходимости.
            Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен \
            только через мобильное приложение или интернет-банкинг.
            Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!""";

    public final static String PRODUCT_NAME_CREDIT = "Простой кредит";
    public final static String PRODUCT_ID_CREDIT = "ab138afb-f3ba-4a93-b74f-0fcee86d447f";
    public final static String PRODUCT_TEXT_CREDIT = """
            Откройте мир выгодных кредитов с нами! \
            Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно \
            то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\
            Почему выбирают нас: \
            Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\
            Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\
            Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, \
            автомобиля, образование, лечение и многое другое.\
            Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!""";

    public final static RecmDTO INVEST = new RecmDTO(PRODUCT_NAME_INVEST, PRODUCT_ID_INVEST, PRODUCT_TEXT_INVEST);
    public final static RecmDTO SAVING = new RecmDTO(PRODUCT_NAME_SAVING, PRODUCT_ID_SAVING, PRODUCT_TEXT_SAVING);
    public final static RecmDTO CREDIT = new RecmDTO(PRODUCT_NAME_CREDIT, PRODUCT_ID_CREDIT, PRODUCT_TEXT_CREDIT);
    public final static List<RecmDTO> recommendations = List.of(INVEST, SAVING, CREDIT);


}