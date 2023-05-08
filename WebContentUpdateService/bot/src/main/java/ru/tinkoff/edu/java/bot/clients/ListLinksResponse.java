package ru.tinkoff.edu.java.bot.clients;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, Integer size) {
}
