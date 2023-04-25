package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.tinkoff.edu.java.scrapper.repositories.JdbcChatsRepository;
import ru.tinkoff.edu.java.scrapper.repositories.JdbcLinksRepository;
import ru.tinkoff.edu.java.scrapper.services.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.services.JdbcLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcChatsRepository jdbcChatsRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcChatsRepository(jdbcTemplate);
    }

    @Bean
    public JdbcChatService jdbcChatService(JdbcChatsRepository jdbcChatsRepository) {
        return new JdbcChatService(jdbcChatsRepository);
    }

    @Bean
    public JdbcLinksRepository jdbcLinksRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcLinksRepository(jdbcTemplate);
    }

    @Bean
    public JdbcLinkService jdbcLinkService(JdbcLinksRepository jdbcLinksRepository) {
        return new JdbcLinkService(jdbcLinksRepository);
    }
}
