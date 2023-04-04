package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverflowClient {
    public static final String DEFAULT_BASE_URL = "https://api.stackexchange.com/2.3";
    private final WebClient webClient;

    public StackOverflowClient() {
        this(StackOverflowClient.DEFAULT_BASE_URL);
    }

    public StackOverflowClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public StackOverflowQuestionResponse fetchQuestionUpdates(long id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/questions/{id}")
                        .queryParam("site", "stackoverflow") //site is required
                        .build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(StackOverflowQuestionResponse.class)
                .block();
    }
}
