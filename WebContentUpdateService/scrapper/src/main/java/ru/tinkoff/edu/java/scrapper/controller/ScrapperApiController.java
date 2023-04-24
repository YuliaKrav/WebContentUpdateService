package ru.tinkoff.edu.java.scrapper.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.services.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.services.JdbcLinkService;

@RestController
@RequestMapping("/api/v1")
public class ScrapperApiController {
    private final JdbcChatService jdbcChatService;
    private final JdbcLinkService jdbcLinkService;

    public ScrapperApiController(JdbcChatService jdbcChatService, JdbcLinkService jdbcLinkService) {
        this.jdbcChatService = jdbcChatService;
        this.jdbcLinkService = jdbcLinkService;
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity registerChat(@PathVariable Long id) {
        jdbcChatService.add(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity deleteChat(@PathVariable Long id) {
        jdbcChatService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/links")
    public ResponseEntity getAllLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        jdbcLinkService.findAll(chatId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/links")
    public ResponseEntity addLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody AddLinkRequest request) {
        jdbcLinkService.add(request.getLink(), chatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/links")
    public ResponseEntity removeLink(@RequestHeader("Tg-Chat-Id") Long chatId, @RequestBody RemoveLinkRequest request) {
        jdbcLinkService.remove(request.getLink(), chatId);
        return ResponseEntity.ok().build();
    }

}
