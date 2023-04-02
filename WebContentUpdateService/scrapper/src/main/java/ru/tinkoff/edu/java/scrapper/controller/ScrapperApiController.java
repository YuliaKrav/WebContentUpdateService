package ru.tinkoff.edu.java.scrapper.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
@RequestMapping("/api/v1")
public class ScrapperApiController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity registerChat(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity deleteChat(@PathVariable Long id) {

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
