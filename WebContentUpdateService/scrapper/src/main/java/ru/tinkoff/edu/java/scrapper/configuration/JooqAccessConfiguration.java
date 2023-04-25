package ru.tinkoff.edu.java.scrapper.configuration;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.repositories.JooqChatsRepository;
import ru.tinkoff.edu.java.scrapper.repositories.JooqLinksRepository;
import ru.tinkoff.edu.java.scrapper.services.JooqChatService;
import ru.tinkoff.edu.java.scrapper.services.JooqLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {

    @Bean
    public JooqChatsRepository jooqChatsRepository(DSLContext dslContext) {
        return new JooqChatsRepository(dslContext);
    }

    @Bean
    public JooqChatService jooqChatService(JooqChatsRepository jooqChatsRepository) {
        return new JooqChatService(jooqChatsRepository);
    }

    @Bean
    public JooqLinksRepository jooqLinksRepository(DSLContext dslContext) {
        return new JooqLinksRepository(dslContext);
    }

    @Bean
    public JooqLinkService jooqLinkService(JooqLinksRepository jooqLinksRepository) {
        return new JooqLinkService(jooqLinksRepository);
    }
}
