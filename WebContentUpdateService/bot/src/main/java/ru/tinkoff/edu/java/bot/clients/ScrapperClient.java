package ru.tinkoff.edu.java.bot.clients;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ScrapperClient {

    private static final String TG_CHAT_ID_HEADER = "Tg-Chat-Id";
    private static final String API_V1_TG_CHAT_ID_PATH = "/api/v1/tg-chat/{id}";
    private static final String API_V1_LINKS_PATH = "/api/v1/links";
    private final WebClient webClient;

    public ScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public RegisterChatRepoResponse registerChat(long chatId) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path(API_V1_TG_CHAT_ID_PATH).build(chatId))
            .exchangeToMono(clientResponse -> Mono.just(new RegisterChatRepoResponse(clientResponse.statusCode()
                .value())))
            .onErrorResume(e -> Mono.error(new RuntimeException("Error registering chat", e))).block();
    }

    public DeleteChatRepoResponse deleteChat(long chatId) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path(API_V1_TG_CHAT_ID_PATH).build(chatId))
            .exchangeToMono(clientResponse -> Mono.just(new DeleteChatRepoResponse(clientResponse.statusCode()
                .value())))
            .onErrorResume(e -> Mono.error(new RuntimeException("Error deleting chat", e))).block();
    }

    public ListLinksResponse getAllLinks(long chatId) {
        return webClient.get()
            .uri(API_V1_LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(chatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    public AddLinkRepoResponse addLink(long chatId, String link) {
        AddLinkRequest addLinkRequest = new AddLinkRequest(link);

        return webClient.post()
            .uri(API_V1_LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(chatId))
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(AddLinkRepoResponse.class)
            .block();
    }

    public RemoveLinkRepoResponse removeLink(long chatId, String link) {
        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(link);

        return webClient.method(HttpMethod.DELETE)
            .uri(API_V1_LINKS_PATH)
            .header(TG_CHAT_ID_HEADER, String.valueOf(chatId))
            .bodyValue(removeLinkRequest)
            .retrieve()
            .bodyToMono(RemoveLinkRepoResponse.class)
            .block();
    }
}

