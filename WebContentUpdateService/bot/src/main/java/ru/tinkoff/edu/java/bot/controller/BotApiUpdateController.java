package ru.tinkoff.edu.java.bot.controller;

import io.micrometer.core.annotation.Timed;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.service.TelegramBotService;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

@Slf4j
@Timed
@RestController
@RequestMapping("/api/v1")
public class BotApiUpdateController {
    private final Map<Integer, LinkUpdateRequest> history = new HashMap<>();
    private final TelegramBotService telegramBotService;

    public BotApiUpdateController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @PostMapping("/updates")
    public ResponseEntity<Void> processUpdate(@RequestBody LinkUpdateRequest linkUpdateRequest) {
        log.info("Update message received (HTTP).");
        telegramBotService.sendNotificationToUser(linkUpdateRequest);
        history.put(linkUpdateRequest.getId(), linkUpdateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/updates", method = RequestMethod.GET)
    public ResponseEntity<LinkUpdateRequest> getUpdate(@PathVariable("id") int id) {
        return new ResponseEntity<>(history.get(id), HttpStatus.OK);
    }

    //Test Python
//import requests
//
//        url = "http://localhost:8080/api/v1/updates"
//        headers = {'Content-Type': 'application/json'}
//        data = {
//        "id": 4,
//        "url": "https://example.com/a",
//        "description": "Example description",
//        "tgChatIds": [0]
//        }
//        response = requests.post(url, headers=headers, json=data)
//
//        if response.status_code == 200:
//        print("Request succeeded")
//        else:
//        print(f"Request failed with status code {response.status_code}")
}


