package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.ChatEntity;
import ru.tinkoff.edu.java.scrapper.repositories.ChatRepository;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void add(Long chatNumber) {
        chatRepository.add(chatNumber);
    }

    public void remove(Long chatNumber) {
        chatRepository.remove(chatNumber);
    }

    public List<ChatEntity> findAll() {
        return chatRepository.findAll();
    }
}
