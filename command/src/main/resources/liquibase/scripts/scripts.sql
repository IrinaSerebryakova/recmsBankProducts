-- liquibase formatted sql

-- changeset IrinaSerebryakova:1
CREATE TABLE rules
(
    id          BIGSERIAL PRIMARY KEY,
    product_id   UUID,
    product_name TEXT,
    product_text TEXT
);

-- changeset IrinaSerebryakova:2
CREATE TABLE query
(
    id        BIGINT PRIMARY KEY,
    query     TEXT,
    arguments TEXT,
    negate    boolean,
    rule_id BIGINT references rules(id)
);

-- changeset IrinaSerebryakova:3
CREATE INDEX query_index ON query (query);

-- changeset IrinaSerebryakova:4
CREATE INDEX rules_productName_index ON rules (product_name);

-- changeset IrinaSerebryakova:5
CREATE TABLE stats
(
    rule_id BIGINT primary key ,
    count INTEGER NOT NULL DEFAULT 0
);

-- changeset IrinaSerebryakova:6
ALTER TABLE stats
    ADD CONSTRAINT fk_rule_id
        FOREIGN KEY (rule_id) REFERENCES query(id);

-- changeset IrinaSerebryakova:7
CREATE INDEX stats_index ON stats (rule_id);

-- changeset IrinaSerebryakova:8
INSERT INTO stats (rule_id)
SELECT id
FROM query;