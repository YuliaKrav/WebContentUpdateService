--liquibase formatted sql

--changeset webapp:1
CREATE TABLE links (
    id SERIAL PRIMARY KEY,
    url VARCHAR(1000) NOT NULL,
    last_update_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    id_chat INTEGER
);
