package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.clients.BotClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverflowClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient gitHubClient(
            @Value("${github.api.base-url:#{'${github.api.default-base-url}'}}") String gitHubBaseUrl) {
        return new GitHubClient(gitHubBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(
            @Value("${stackoverflow.api.base-url:#{'${stackoverflow.api.default-base-url}'}}") String stackOverflowBaseUrl) {
        return new StackOverflowClient(stackOverflowBaseUrl);
    }

    @Bean
    public BotClient scrapperClient(
            @Value("${bot.api.base-url:#{'${bot.api.default-base-url}'}}")
            String scrapperBaseUrl) {
        return new BotClient(scrapperBaseUrl);
    }


}
