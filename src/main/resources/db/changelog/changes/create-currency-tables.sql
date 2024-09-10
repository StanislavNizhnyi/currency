--liquibase formatted sql

--changeset snyzhnyi:01
CREATE TABLE IF NOT EXISTS currency(
    code BIGINT NOT NULL CONSTRAINT code_pkey PRIMARY KEY,
    name TEXT NOT NULL
)

--changeset snyzhnyi:02
CREATE TABLE IF NOT EXISTS currency_log(
    id BIGINT NOT NULL,
    currency_source_name TEXT NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_currency_log_id PRIMARY KEY(id)
);

CREATE SEQUENCE currency_log_seq START 1 INCREMENT BY 50;
