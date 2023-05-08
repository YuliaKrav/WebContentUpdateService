package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;


public interface JpaLinksRepository extends JpaRepository<LinkEntity, Integer> {
    void deleteByUrlAndChatEntity_ChatNumber(String url, Long chatEntity_chatNumber);
    @Transactional
    default void updateLastUpdateDate(String url, Long chatNumber, OffsetDateTime lastUpdateDate) {
        LinkEntity linkEntity = findByUrlAndChatEntity_ChatNumber(url, chatNumber);
        if (linkEntity != null) {
            linkEntity.setLastUpdateDate(lastUpdateDate);
            save(linkEntity);
        }
    }

    LinkEntity findByUrlAndChatEntity_ChatNumber(String url, Long chatNumber);

    List<LinkEntity> findAllByChatEntity_ChatNumber(Long chatEntity_chatNumber);

    List<LinkEntity> findAllByLastUpdateDateBefore(OffsetDateTime lastUpdateDate);
    List<LinkEntity> findAllByUrl(String url);

    LinkEntity findByUrl(String url); // for JPA test
}
