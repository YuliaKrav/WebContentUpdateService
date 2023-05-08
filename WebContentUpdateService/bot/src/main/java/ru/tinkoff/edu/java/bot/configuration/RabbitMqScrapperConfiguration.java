package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "true")
public class RabbitMqScrapperConfiguration {

    @Bean
    public ScrapperQueueListener scrapperQueueListener(TelegramBotService telegramBotService) {
        return new ScrapperQueueListener(telegramBotService);
    }
}
