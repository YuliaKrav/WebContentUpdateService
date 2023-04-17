package ru.tinkoff.edu.java.bot.clients;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ApiException;

public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public RegisterChatRepoResponse registerChat(long chatId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/tg-chat/{id}").build(chatId))
                .exchangeToMono(clientResponse -> Mono.just(new RegisterChatRepoResponse(clientResponse.statusCode().value())))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error registering chat", e))).block();
    }

    public DeleteChatRepoResponse deleteChat(long chatId) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/tg-chat/{id}").build(chatId))
                .exchangeToMono(clientResponse -> Mono.just(new DeleteChatRepoResponse(clientResponse.statusCode().value())))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting chat", e))).block();
    }

    public GetAllLinksRepoResponse getAllLinks(long chatId) {
        return webClient.get()
                .uri("/api/v1/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .retrieve()
                .bodyToMono(GetAllLinksRepoResponse.class).block();
    }

    public AddLinkRepoResponse addLink(long chatId, String link) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(link);

        return webClient.post()
                .uri("/api/v1/links")
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .bodyValue(addLinkRequest)
                .retrieve()
                .bodyToMono(AddLinkRepoResponse.class).block();
    }

    public RemoveLinkRepoResponse removeLink(long chatId, String link) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/links")
                        .queryParam("link", link)
                        .build())
                .header("Tg-Chat-Id", String.valueOf(chatId))
                .exchangeToMono(response -> {
                    int statusCode = response.statusCode().value();
                    if (statusCode == 200) {
                        return response.bodyToMono(LinkResponse.class)
                                .map(linkResponse -> new RemoveLinkRepoResponse(statusCode, linkResponse.toString()));
                    } else {
                        return response.bodyToMono(ApiErrorResponse.class)
                                .flatMap(errorResponse -> Mono.error(new ApiException(statusCode, errorResponse.toString())));
                    }
                })
                .block();
    }
}

