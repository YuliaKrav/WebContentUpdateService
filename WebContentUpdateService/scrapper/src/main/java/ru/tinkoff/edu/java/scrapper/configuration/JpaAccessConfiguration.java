package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repositories.JpaChatsRepository;
import ru.tinkoff.edu.java.scrapper.repositories.JpaLinksRepository;
import ru.tinkoff.edu.java.scrapper.services.JpaChatService;
import ru.tinkoff.edu.java.scrapper.services.JpaLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JpaChatService jdbcChatService(JpaChatsRepository jpaChatsRepository) {
        return new JpaChatService(jpaChatsRepository);
    }

    @Bean
    public JpaLinkService jdbcLinkService(JpaLinksRepository jpaLinksRepository) {
        return new JpaLinkService(jpaLinksRepository);
    }
}
