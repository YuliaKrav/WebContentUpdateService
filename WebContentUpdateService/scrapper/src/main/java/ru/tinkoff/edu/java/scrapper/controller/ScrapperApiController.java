package ru.tinkoff.edu.java.scrapper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@RestController
@RequestMapping("/api/v1")
public class ScrapperApiController {
    private final ChatService chatService;
    private final LinkService linkService;

    public ScrapperApiController(ChatService chatService, LinkService linkService) {
        this.chatService = chatService;
        this.linkService = linkService;
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity registerChat(@PathVariable Long id) {
        chatService.add(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity deleteChat(@PathVariable Long id) {
        chatService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getAllLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        ListLinksResponse listLinksResponse = linkService.findAll(chatId);
        return ResponseEntity.ok(listLinksResponse);
    }

    @PostMapping("/links")
    public ResponseEntity addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        linkService.add(request.getLink(), chatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/links")
    public ResponseEntity removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        linkService.remove(request.getLink(), chatId);
        return ResponseEntity.ok().build();
    }

}
