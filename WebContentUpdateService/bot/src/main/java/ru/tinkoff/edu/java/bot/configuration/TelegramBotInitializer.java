package ru.tinkoff.edu.java.bot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;

@Configuration
public class TelegramBotInitializer {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotInitializer.class);

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramBotService botService) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(botService);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            logger.error("Failed to register bot: {}", e.getMessage(), e);
            throw new IllegalStateException("Failed to register bot.", e);
        }
    }

    @Bean
    public BotSession botSession() {
        return new DefaultBotSession();
    }

}
