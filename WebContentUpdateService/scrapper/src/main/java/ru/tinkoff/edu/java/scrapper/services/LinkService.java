package ru.tinkoff.edu.java.scrapper.services;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;

import java.time.OffsetDateTime;
import java.util.List;

public interface LinkService {

    void add(String url, Long chatNumber);
    void remove(String url, Long chatNumber);
    List<LinkDto> findAll(Long chatId);

    List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime);
}
