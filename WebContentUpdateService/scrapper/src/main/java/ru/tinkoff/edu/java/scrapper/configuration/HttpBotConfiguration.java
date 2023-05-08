package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.services.BotService;
import ru.tinkoff.edu.java.scrapper.services.HttpBotService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "useQueue", havingValue = "false")
public class HttpBotConfiguration {
    @Bean
    public BotClient botClient(@Value("${bot.api.base-url:#{'${bot.api.default-base-url}'}}")
                               String baseUrl) {
        return new BotClient(baseUrl);
    }

    @Bean
    public BotService botService() {
        return new HttpBotService();
    }
}
