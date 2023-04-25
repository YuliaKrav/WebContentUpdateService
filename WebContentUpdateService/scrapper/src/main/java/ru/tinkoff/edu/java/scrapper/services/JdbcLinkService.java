package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.repositories.JdbcLinksRepository;

import java.time.OffsetDateTime;
import java.util.List;


public class JdbcLinkService implements LinkService {
    private final JdbcLinksRepository jdbcLinksRepository;

    public JdbcLinkService(JdbcLinksRepository jdbcLinksRepository) {
        this.jdbcLinksRepository = jdbcLinksRepository;
    }

    @Override
    public void add(String url, Long chatNumber) {
        jdbcLinksRepository.add(url, OffsetDateTime.now(), chatNumber);
    }

    @Override
    public void remove(String url, Long chatNumber) {
        jdbcLinksRepository.remove(url, chatNumber);
    }

    @Override
    public List<LinkDto> findAll(Long chatId) {
        return jdbcLinksRepository.findAll(chatId);
    }

    @Override
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return jdbcLinksRepository.findAllOutdatedLinks(dateTime);
    }
}
