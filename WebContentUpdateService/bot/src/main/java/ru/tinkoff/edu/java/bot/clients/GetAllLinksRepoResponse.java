package ru.tinkoff.edu.java.bot.clients;

import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;

import java.util.List;

public record GetAllLinksRepoResponse(List<LinkResponse> links, Integer size) {
}
