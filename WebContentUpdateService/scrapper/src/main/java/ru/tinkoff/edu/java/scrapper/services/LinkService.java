package ru.tinkoff.edu.java.scrapper.services;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {

    void add(String url, Long chatNumber);
    void remove(String url, Long chatNumber);
    List<LinkEntity> findAll(Long chatId);

    List<LinkEntity> findAllOutdatedLinks(OffsetDateTime dateTime);
}
