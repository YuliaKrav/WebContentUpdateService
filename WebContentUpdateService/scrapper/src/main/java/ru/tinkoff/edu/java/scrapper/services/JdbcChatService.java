package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.repositories.JdbcChatsRepository;

import java.util.List;


public class JdbcChatService implements ChatService {
    private final JdbcChatsRepository jdbcChatsRepository;

    public JdbcChatService(JdbcChatsRepository jdbcChatsRepository) {
        this.jdbcChatsRepository = jdbcChatsRepository;
    }

    @Override
    public void add(Long chatNumber) {
        jdbcChatsRepository.add(chatNumber, "user");
    }

    @Override
    public void remove(Long chatNumber) {
        jdbcChatsRepository.remove(chatNumber);
    }

    @Override
    public List<ChatDto> findAll() {
        return jdbcChatsRepository.findAll();
}
}
