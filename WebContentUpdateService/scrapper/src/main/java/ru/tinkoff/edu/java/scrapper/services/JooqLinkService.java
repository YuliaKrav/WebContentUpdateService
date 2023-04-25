package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repositories.JooqChatsRepository;
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
    public List<LinkEntity> findAll(Long chatId) {
        return jooqLinksRepository.findAll(chatId);
    }

    @Override
    public List<LinkEntity> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return jooqLinksRepository.findAllOutdatedLinks(dateTime);
    }
}
