package ru.tinkoff.edu.java.scrapper.services;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.repositories.JpaChatsRepository;
import ru.tinkoff.edu.java.scrapper.repositories.JpaLinksRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class JpaLinkService implements LinkService{
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
    public void remove(String url, Long chatNumber) {
        jpaLinksRepository.deleteByUrlAndChatEntity_ChatNumber(url, chatNumber);
    }

    @Override
    public List<LinkDto> findAll(Long chatId) {
        return jpaLinksRepository.findAllByChatEntity_ChatNumber(chatId)
                .stream()
                .map(entity -> new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber()))
                .collect(Collectors.toList());
    }


    @Override
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {

        return jpaLinksRepository.findAllByLastUpdateDateBefore(dateTime)
                .stream()
                .map(entity -> new LinkDto(entity.getId(), entity.getUrl(), entity.getLastUpdateDate(), entity.getChatEntity().getChatNumber()))
                .collect(Collectors.toList());
    }
}
