package ru.tinkoff.edu.java.scrapper.clients;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

public class BotClient {
    private final WebClient webClient;

    public BotClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void postUpdates(LinkUpdateRequest linkUpdateRequest) {
        webClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/updates").build())
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
    }
}
