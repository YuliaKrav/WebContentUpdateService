package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.ChatEntity;
import ru.tinkoff.edu.java.scrapper.repositories.ChatsRepository;

import java.util.List;

@Service
public class JdbcChatService implements ChatService {
    private final ChatsRepository chatsRepository;

    public JdbcChatService(ChatsRepository chatsRepository) {
        this.chatsRepository = chatsRepository;
    }

    public void add(Long chatNumber) {
        chatsRepository.add(chatNumber, "user");
    }

    public void remove(Long chatNumber) {
        chatsRepository.remove(chatNumber);
    }

    public List<ChatEntity> findAll() {
        return chatsRepository.findAll();
    }
}
