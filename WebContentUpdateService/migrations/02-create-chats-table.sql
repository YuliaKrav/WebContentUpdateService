--liquibase formatted sql

--changeset webapp:3
CREATE TABLE chats (
    chat_number INTEGER PRIMARY KEY,
    user_name VARCHAR(300)
)

--changeset webapp:4
ALTER TABLE links
    ADD FOREIGN KEY (id_chat) REFERENCES chats (chat_number)
    ON DELETE CASCADE