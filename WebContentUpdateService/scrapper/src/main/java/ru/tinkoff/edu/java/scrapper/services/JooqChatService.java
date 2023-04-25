package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.repositories.JooqChatsRepository;

import java.util.List;


public class JooqChatService implements ChatService{

    private final JooqChatsRepository jooqChatsRepository;

    public JooqChatService(JooqChatsRepository jooqChatsRepository) {
        this.jooqChatsRepository = jooqChatsRepository;
    }

    @Override
    public void add(Long chatNumber) {
        jooqChatsRepository.add(chatNumber, "user");
    }

    @Override
    public void remove(Long chatNumber) {
        jooqChatsRepository.remove(chatNumber);
    }

    @Override
    public List<ChatDto> findAll() {
        return jooqChatsRepository.findAll();
    }
}
