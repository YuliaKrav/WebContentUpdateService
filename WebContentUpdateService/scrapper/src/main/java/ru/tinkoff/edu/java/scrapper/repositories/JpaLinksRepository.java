package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;


public interface JpaLinksRepository extends JpaRepository<LinkEntity, Integer> {
    void deleteByUrlAndChatEntity_ChatNumber(String url, Long chatEntity_chatNumber);
    List<LinkEntity> findAllByChatEntity_ChatNumber(Long chatEntity_chatNumber);

    List<LinkEntity> findAllByLastUpdateDateBefore(OffsetDateTime lastUpdateDate);
    List<LinkEntity> findAllByUrl(String url);

    LinkEntity findByUrl(String url); // for JPA test
}
