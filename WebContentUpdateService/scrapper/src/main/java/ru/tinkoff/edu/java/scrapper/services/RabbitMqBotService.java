package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

public class RabbitMqBotService implements BotService {

    @Autowired
    private ScrapperQueueProducer scrapperQueueProducer;

    @Override
    public void sendUpdates(LinkUpdateRequest linkUpdateRequest) {
        scrapperQueueProducer.sendUpdates(linkUpdateRequest);
    }
}
