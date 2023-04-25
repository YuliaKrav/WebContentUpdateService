package ru.tinkoff.edu.java.scrapper.repositories;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chats;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;

import java.util.List;


public class JooqChatsRepository {
    private final DSLContext dslContext;

    @Autowired
    public JooqChatsRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    public void add(Long chatNumber, String userName) {
        ChatsRecord chatsRecord = dslContext.newRecord(Chats.CHATS);
        chatsRecord.setChatNumber(chatNumber.intValue());
        chatsRecord.setUserName(userName);
        chatsRecord.store();
    }

    @Transactional
    public void remove(Long chatNumber) {
        dslContext.deleteFrom(Chats.CHATS)
                .where(Chats.CHATS.CHAT_NUMBER.eq(chatNumber.intValue()))
                .execute();
    }

    @Transactional
    public List<ChatDto> findAll() {
        return dslContext.selectFrom(Chats.CHATS)
                .fetch()
                .map(record -> new ChatDto(record.getChatNumber().longValue(), record.getUserName()));
    }
}
