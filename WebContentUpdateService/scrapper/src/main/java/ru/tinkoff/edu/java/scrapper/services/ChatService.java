package ru.tinkoff.edu.java.scrapper.services;

import java.util.List;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;

public interface ChatService {
    void add(Long chatNumber);

    void remove(Long chatNumber);

    List<ChatDto> findAll();
}
