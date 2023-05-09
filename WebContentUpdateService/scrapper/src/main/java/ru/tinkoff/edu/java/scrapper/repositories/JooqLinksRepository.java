package ru.tinkoff.edu.java.scrapper.repositories;

import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;

public class JooqLinksRepository {
    private final DSLContext dslContext;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplate.class);

    @Autowired
    public JooqLinksRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Transactional
    public void add(String url, OffsetDateTime lastUpdateDate, Long chatNumber) {
        //TODO добавить сообщениедля пользователя о дублировании ссылки для отслеживания
        try {
            dslContext.insertInto(Links.LINKS)
                .set(Links.LINKS.URL, url)
                .set(Links.LINKS.LAST_UPDATE_DATE, lastUpdateDate).set(Links.LINKS.ID_CHAT, chatNumber.intValue())
                .execute();
        } catch (DataAccessException ex) {
            LOGGER.info("This link is already tracked");
        }
    }

    @Transactional
    public void remove(String url, Long chatNumber) {
        dslContext.deleteFrom(Links.LINKS)
            .where(Links.LINKS.URL.eq(url))
            .and(Links.LINKS.ID_CHAT.eq(chatNumber.intValue()))
            .execute();
    }

    @Transactional
    public void updateLastUpdateDate(String url, Long chatNumber, OffsetDateTime lastUpdateDate) {
        dslContext.update(Links.LINKS)
            .set(Links.LINKS.LAST_UPDATE_DATE, lastUpdateDate)
            .where(Links.LINKS.URL.eq(url))
            .and(Links.LINKS.ID_CHAT.eq(chatNumber.intValue()))
            .execute();
    }

    @Transactional
    public List<LinkDto> findAll(Long chatId) {
        return dslContext.selectFrom(Links.LINKS)
            .where(Links.LINKS.ID_CHAT.eq(chatId.intValue()))
            .fetch()
            .map(rec -> new LinkDto(
                    rec.getId(),
                    rec.getUrl(),
                    rec.getLastUpdateDate(),
                    rec.getIdChat().longValue()
                )
            );
    }

    @Transactional
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return dslContext.selectFrom(Links.LINKS)
            .where(Links.LINKS.LAST_UPDATE_DATE.lt(dateTime))
            .fetch()
            .map(rec -> new LinkDto(
                    rec.getId(),
                    rec.getUrl(),
                    rec.getLastUpdateDate(),
                    rec.getIdChat().longValue()
                )
            );
    }

    public List<Long> findAllChatsIdByUrl(String url) {
        return dslContext.select(Links.LINKS.ID_CHAT)
            .from(Links.LINKS)
            .where(Links.LINKS.URL.eq(url))
            .fetch()
            .into(Long.class);
    }
}
