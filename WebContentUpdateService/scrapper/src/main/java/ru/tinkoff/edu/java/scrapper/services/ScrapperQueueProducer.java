package ru.tinkoff.edu.java.scrapper.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

@Slf4j
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    public void sendUpdates(LinkUpdateRequest linkUpdateRequest) {
        log.info("Send update message to bot.");
        rabbitTemplate.convertAndSend(directExchange.getName(), routingKey, linkUpdateRequest);
    }
}
