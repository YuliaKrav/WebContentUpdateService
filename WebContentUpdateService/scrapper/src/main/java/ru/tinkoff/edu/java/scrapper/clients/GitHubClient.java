package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class GitHubClient {
    public static final String DEFAULT_BASE_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubClient() {
        this(GitHubClient.DEFAULT_BASE_URL);
    }

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
