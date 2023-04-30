package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.repositories.JooqLinksRepository;

import java.time.OffsetDateTime;
import java.util.List;


public class JooqLinkService implements LinkService {

    private final JooqLinksRepository jooqLinksRepository;

    public JooqLinkService(JooqLinksRepository jooqLinksRepository) {
        this.jooqLinksRepository = jooqLinksRepository;
    }

    @Override
    public void add(String url, Long chatNumber) {
        jooqLinksRepository.add(url, OffsetDateTime.now(), chatNumber);
    }

    @Override
    public void remove(String url, Long chatNumber) {
        jooqLinksRepository.remove(url, chatNumber);
    }

    @Override
    public List<Long> findAllChatsIdByUrl(String url) {
        return jooqLinksRepository.findAllChatsIdByUrl(url);
    }

    @Override
    public List<LinkDto> findAll() {
        return null;
    }

    @Override
    public ListLinksResponse findAll(Long chatId) {
        return convertLinkDtoListToListLinksResponse(jooqLinksRepository.findAll(chatId));
    }

    @Override
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return jooqLinksRepository.findAllOutdatedLinks(dateTime);
    }
}
