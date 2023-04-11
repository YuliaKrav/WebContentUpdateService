--liquibase formatted sql

--changeset webapp:2
CREATE TABLE chats (
    id SERIAL PRIMARY KEY,
    chat_number INTEGER,
    user_name VARCHAR(300)
)

--changeset webapp:3
ALTER TABLE links
    ADD FOREIGN KEY (id_chat) REFERENCES chats (id)
    ON DELETE CASCADE