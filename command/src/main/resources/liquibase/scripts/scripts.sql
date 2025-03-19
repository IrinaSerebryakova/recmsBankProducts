-- liquibase formatted sql

-- changeset IrinaSerebryakova:1
CREATE TABLE dynamic_rules
(
    id        SERIAL PRIMARY KEY,
    query     TEXT,
    arguments TEXT ARRAY,
    negate    boolean,
    recommendation_id INTEGER
);

-- changeset IrinaSerebryakova:2
CREATE TABLE recommendations
(
    id          SERIAL PRIMARY KEY,
    productName TEXT,
    productId   UUID,
    productText TEXT,
    rule        TEXT ARRAY
);

-- changeset IrinaSerebryakova:3
CREATE INDEX dynamic_rules_index ON dynamic_rules (query);

-- changeset IrinaSerebryakova:4
CREATE INDEX recommendations_productName_index ON recommendations (productName);


-- changeset IrinaSerebryakova:5
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('USER_OF', ARRAY ['DEBIT'], true, 1);

-- changeset IrinaSerebryakova:6
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('USER_OF',  ARRAY['INVEST'], true, 1);

-- changeset IrinaSerebryakova:7
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('TRANSACTION_SUM_COMPARE',  ARRAY['DEPOSIT', 'SAVING', '>', '1000'], true, 1);

-- changeset IrinaSerebryakova:8
INSERT INTO recommendations (id, productName, productId, productText, rule)
VALUES (
        1,
    'Invest 500', '147f6a0f-3b91-413b-ab99-87f081d60d5a'::UUID,
        'Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! ' ||
        'Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца ' ||
        'года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите  ' ||
        'возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными ' ||
        'тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!',
        ARRAY (
                SELECT ROW(query, arguments, negate)
                FROM dynamic_rules
                WHERE recommendation_id = 1
        )
       );

-- changeset IrinaSerebryakova:9
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('USER_OF', ARRAY ['DEBIT'], true, 2);

-- changeset IrinaSerebryakova:10
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('TRANSACTION_SUM_COMPARE',  ARRAY['DEPOSIT', 'DEBIT', '>=', '50000'], true, 2);

-- changeset IrinaSerebryakova:11
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW',  ARRAY['DEBIT', '>'], true, 2);

-- changeset IrinaSerebryakova:12
INSERT INTO recommendations (id, productName, productId, productText, rule)
VALUES (
        2,
        'Top Saving',
        '59efc529-2fff-41af-baff-90ccd7402925'::UUID,
        'Откройте свою собственную «Копилку» с нашим банком! ' ||
        '«Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать ' ||
        'деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем! ' ||
        'Преимущества «Копилки»: ' ||
        'Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет ' ||
        'автоматически переводить определенную сумму на ваш счет. ' ||
        'Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления  ' ||
        'и корректируйте стратегию при необходимости. ' ||
        'Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен ' ||
        'только через мобильное приложение или интернет-банкинг. ' ||
        'Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!',
        ARRAY (
                SELECT ROW(query, arguments, negate)
                FROM dynamic_rules
                WHERE recommendation_id = 2
        )
       );

-- changeset IrinaSerebryakova:13
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW',  ARRAY['DEBIT', '>'], true, 3);

-- changeset IrinaSerebryakova:14
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('USER_OF', ARRAY ['CREDIT'], true, 3);

-- changeset IrinaSerebryakova:15
INSERT INTO dynamic_rules (query, arguments, negate, recommendation_id) VALUES ('TRANSACTION_SUM_COMPARE',  ARRAY['WITHDRAW', 'DEBIT', '>', '100000'], true, 3);

-- changeset IrinaSerebryakova:16
INSERT INTO recommendations (id, productName, productId, productText, rule)
VALUES (3, 'Простой кредит', 'ab138afb-f3ba-4a93-b74f-0fcee86d447f'::UUID,
        'Откройте мир выгодных кредитов с нами! ' ||
        'Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно ' ||
        'то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту. ' ||
        'Почему выбирают нас: ' ||
        'Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов. ' ||
        'Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении. ' ||
        'Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, ' ||
        'автомобиля, образование, лечение и многое другое. ' ||
        'Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!',
        ARRAY (
                SELECT ROW(query, arguments, negate)
                FROM dynamic_rules
                WHERE recommendation_id = 3
        )
       );