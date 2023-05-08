package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.repositories.JpaChatsRepository;

import java.util.List;
import java.util.stream.Collectors;


public class JpaChatService implements ChatService {
    private final JpaChatsRepository jpaChatsRepository;

    public JpaChatService(JpaChatsRepository jpaChatsRepository) {
        this.jpaChatsRepository = jpaChatsRepository;
    }

    @Override
    public void add(Long chatNumber) {
        jpaChatsRepository.save(ChatEntity.builder()
                .chatNumber(chatNumber)
                .userName("user").build());
    }

    @Override
    public void remove(Long chatNumber) {
        jpaChatsRepository.deleteById(chatNumber);
    }

    @Override
    public List<ChatDto> findAll() {
        return jpaChatsRepository.findAll()
                .stream()
                .map(entity -> new ChatDto(entity.getChatNumber(), entity.getUserName()))
                .collect(Collectors.toList());
    }

    public ChatDto findByChatNumber(Long chatNumber) { // for JPA test
        ChatEntity entity = jpaChatsRepository.findByChatNumber(chatNumber);
        if (entity == null) {
            return null;
        }
        return new ChatDto(entity.getChatNumber(), entity.getUserName());
    }
}
