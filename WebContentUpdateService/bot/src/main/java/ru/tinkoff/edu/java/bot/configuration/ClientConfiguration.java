package ru.tinkoff.edu.java.bot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public ScrapperClient scrapperClient(
        @Value("${scrapper.api.base-url:#{'${scrapper.api.default-base-url}'}}")
        String scrapperBaseUrl
    ) {
        return new ScrapperClient(scrapperBaseUrl);
    }
}
