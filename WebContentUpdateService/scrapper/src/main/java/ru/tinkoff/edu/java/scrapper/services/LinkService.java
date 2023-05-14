package ru.tinkoff.edu.java.scrapper.services;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;

public interface LinkService {

    void add(String url, Long chatNumber);

    void remove(String url, Long chatNumber);

    void updateLastUpdateDate(String url, Long chatNumber, OffsetDateTime lastUpdateDate);

    List<Long> findAllChatsIdByUrl(String url);

    List<LinkDto> findAll();

    ListLinksResponse findAll(Long chatId);

    List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime);

    default ListLinksResponse convertLinkDtoListToListLinksResponse(List<LinkDto> linkDtoList) {
        List<LinkResponse> linkResponses = linkDtoList.stream()
            .map(linkDto -> new LinkResponse((long) linkDto.getId(), linkDto.getUrl()))
            .collect(Collectors.toList());

        ListLinksResponse listLinksResponse = new ListLinksResponse(linkResponses, linkResponses.size());
        return listLinksResponse;
    }
}
