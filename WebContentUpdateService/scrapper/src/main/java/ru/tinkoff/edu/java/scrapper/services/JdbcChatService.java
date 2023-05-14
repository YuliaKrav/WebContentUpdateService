package ru.tinkoff.edu.java.scrapper.services;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.repositories.JdbcChatsRepository;

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
