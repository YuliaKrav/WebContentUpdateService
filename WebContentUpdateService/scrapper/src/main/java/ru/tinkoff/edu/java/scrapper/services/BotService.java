package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

public interface BotService {
    void sendUpdates(LinkUpdateRequest linkUpdateRequest);
}
