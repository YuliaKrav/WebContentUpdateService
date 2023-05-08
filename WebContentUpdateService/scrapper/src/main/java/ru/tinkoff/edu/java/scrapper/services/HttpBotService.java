package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;

public class HttpBotService implements BotService{
    @Autowired
    private BotClient botClient;

    @Override
    public void sendUpdates(LinkUpdateRequest linkUpdateRequest) {
        botClient.postUpdates(linkUpdateRequest);
    }
}
