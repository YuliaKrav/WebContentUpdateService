package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repositories.LinksRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class JdbcLinkService implements LinkService {
    private final LinksRepository linksRepository;

    public JdbcLinkService(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    @Override
    public void add(String url, Long chatNumber) {
        linksRepository.add(url, OffsetDateTime.now(), chatNumber);
    }

    @Override
    public void remove(String url, Long chatNumber) {
        linksRepository.remove(url, chatNumber);
    }

    @Override
    public List<LinkEntity> findAll(Long chatId) {
        return linksRepository.findAll(chatId);
    }

    @Override
    public List<LinkEntity> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return linksRepository.findAllOutdatedLinks(dateTime);
    }
}
