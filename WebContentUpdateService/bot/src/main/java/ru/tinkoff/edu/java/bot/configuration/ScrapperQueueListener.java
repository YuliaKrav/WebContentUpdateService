package ru.tinkoff.edu.java.bot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

@Slf4j
//@Component
@RabbitListener(queues = "${rabbitmq.queue}")
public class ScrapperQueueListener {
    private final TelegramBotService telegramBotService;

    public ScrapperQueueListener(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @RabbitHandler
    public void receiver(LinkUpdateRequest linkUpdateRequest) {
         try {
            log.info("Update message received (RabbitMQ).");
            telegramBotService.sendNotificationToUser(linkUpdateRequest);
        } catch (Exception e) {
            log.warn("Error while processing the message:", e);
        }
    }
}
