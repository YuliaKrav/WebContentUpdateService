package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.ChatDto;

import java.util.List;

public interface ChatService {
    void add(Long chatNumber);

    void remove(Long chatNumber);

    List<ChatDto> findAll();
}
