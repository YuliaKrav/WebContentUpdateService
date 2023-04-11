package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public GitHubRepoResponse fetchRepoUpdates(String user, String repo) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/repos/{user}/{repo}")
                        .build(user, repo))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GitHubRepoResponse.class)
                .block();
    }
}
