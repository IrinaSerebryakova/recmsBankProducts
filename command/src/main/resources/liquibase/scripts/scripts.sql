-- liquibase formatted sql

-- changeset IrinaSerebryakova:1
CREATE TABLE recommendations
(
    id          BIGSERIAL PRIMARY KEY,
    product_name TEXT,
    product_id   UUID,
    product_text TEXT
);

-- changeset IrinaSerebryakova:2
CREATE TABLE dynamic_rules
(
    id        BIGINT PRIMARY KEY,
    query     varchar(255),
    arguments TEXT,
    negate    boolean,
    recommendation_id BIGINT references recommendations(id)
);


-- changeset IrinaSerebryakova:3
CREATE INDEX dynamic_rules_index ON dynamic_rules (query);

-- changeset IrinaSerebryakova:4
CREATE INDEX recommendations_productName_index ON recommendations (product_name);

-- changeset IrinaSerebryakova:17
CREATE TABLE stats
(
    rule_id BIGINT primary key ,
    count INTEGER NOT NULL DEFAULT 0
);

/*-- changeset IrinaSerebryakova:18
ALTER TABLE stats
    ADD CONSTRAINT fk_rule_id
        FOREIGN KEY (rule_id) REFERENCES dynamic_rules(id);
*/
-- changeset IrinaSerebryakova:19
CREATE INDEX stats_index ON stats (rule_id);
--
-- -- changeset IrinaSerebryakova:20
-- INSERT INTO stats (rule_id)
-- SELECT id
-- FROM dynamic_rules;