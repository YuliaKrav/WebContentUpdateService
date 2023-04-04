package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverflowClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public GitHubClient gitHubClient(
            @Value("${github.api.base-url:" + GitHubClient.DEFAULT_BASE_URL + "}") String gitHubBaseUrl) {
        return new GitHubClient(gitHubBaseUrl);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(
            @Value("${stackoverflow.api.base-url:" + StackOverflowClient.DEFAULT_BASE_URL + "}") String stackOverflowBaseUrl) {
        return new StackOverflowClient(stackOverflowBaseUrl);
    }


}
