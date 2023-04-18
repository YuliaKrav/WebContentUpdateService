package ru.tinkoff.edu.java.scrapper.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.services.ChatService;

@RestController
@RequestMapping("/api/v1")
public class ScrapperApiController {
    private final ChatService chatService;

    public ScrapperApiController(ChatService chatService) {
        this.chatService = chatService;
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
    public ResponseEntity getAllLinks(@RequestHeader("Tg-Chat-Id") Integer chatId) {

        return ResponseEntity.ok().build();
    }

    @PostMapping("/links")
    public ResponseEntity addLink(@RequestHeader("Tg-Chat-Id") Integer chatId, @RequestBody AddLinkRequest request) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/links")
    public ResponseEntity removeLink(@RequestHeader("Tg-Chat-Id") Integer chatId, @RequestBody RemoveLinkRequest request) {

        return ResponseEntity.ok().build();
    }

}
