package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repositories.JpaLinksRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaLinkService implements LinkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaLinkService.class);

    private final JpaLinksRepository jpaLinksRepository;

    public JpaLinkService(JpaLinksRepository jpaLinksRepository) {
        this.jpaLinksRepository = jpaLinksRepository;
    }

    @Override
    public void add(String url, Long chatNumber) {
        jpaLinksRepository.save(LinkEntity.builder()
                .url(url)
                .lastUpdateDate(OffsetDateTime.now())
                .chatEntity(ChatEntity.builder().chatNumber(chatNumber).build())
                .build());
    }

    @Override
    @Transactional
    public void remove(String url, Long chatNumber) {
        LOGGER.info("Removing link with URL: {} and chatNumber: {}", url, chatNumber);
        jpaLinksRepository.deleteByUrlAndChatEntity_ChatNumber(url, chatNumber);
    }

    @Override
    public void updateLastUpdateDate(String url, Long chatNumber, OffsetDateTime lastUpdateDate) {
        jpaLinksRepository.updateLastUpdateDate(url, chatNumber, lastUpdateDate);
    }

    @Override
    public List<Long> findAllChatsIdByUrl(String url) {
        return jpaLinksRepository.findAllByUrl(url)
                .stream()
                .map(entity -> entity.getChatEntity().getChatNumber())
                .collect(Collectors.toList());

    }

    @Override
    public List<LinkDto> findAll() {
        return jpaLinksRepository.findAll()
                .stream()
                .map(entity -> new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber()))
                .collect(Collectors.toList());
    }

    @Override
    public ListLinksResponse findAll(Long chatId) {
        List<LinkDto> linkDtoList = jpaLinksRepository.findAllByChatEntity_ChatNumber(chatId)
                .stream()
                .map(entity -> new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber()))
                .collect(Collectors.toList());
        return convertLinkDtoListToListLinksResponse(linkDtoList);
    }

    @Override
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {

        return jpaLinksRepository.findAllByLastUpdateDateBefore(dateTime)
                .stream()
                .map(entity -> new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber()))
                .collect(Collectors.toList());
    }

    public LinkDto findByUrl(String url) { // for JPA test
        LinkEntity entity = jpaLinksRepository.findByUrl(url);
        if (entity == null) {
            return null;
        }
        return new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber());
    }
}
