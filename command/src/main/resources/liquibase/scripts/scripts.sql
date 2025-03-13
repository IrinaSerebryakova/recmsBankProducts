-- liquibase formatted sql
-- changeset IrinaSerebryakova:2
CREATE TABLE dynamic_rules (id SERIAL, query TEXT, arguments character, negate boolean);

-- changeset IrinaSerebryakova:3
CREATE INDEX dynamic_rules_index ON dynamic_rules(query);

-- changeset IrinaSerebryakova:4
ALTER TABLE dynamic_rules ADD PRIMARY KEY (id);
