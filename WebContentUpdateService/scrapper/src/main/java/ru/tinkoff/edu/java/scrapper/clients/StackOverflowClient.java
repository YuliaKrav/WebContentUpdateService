package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.util.List;

public class StackOverflowClient {
    private final WebClient webClient;

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

    public StackOverflowQuestionResponse fetchNewAnswers(long id, OffsetDateTime fromDate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/questions/{id}")
                        .queryParam("site", "stackoverflow")
                        .queryParam("fromdate", fromDate.toEpochSecond())
                        .build(id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(StackOverflowQuestionResponse.class)
                .block();
    }
}
