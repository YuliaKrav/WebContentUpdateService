package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;


public interface JpaChatsRepository extends JpaRepository<ChatEntity, Long> {
    ChatEntity findByChatNumber(Long chatNumber); //for JPA test
}
