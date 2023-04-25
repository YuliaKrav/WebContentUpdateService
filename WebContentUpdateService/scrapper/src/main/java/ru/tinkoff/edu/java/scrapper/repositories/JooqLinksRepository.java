package ru.tinkoff.edu.java.scrapper.repositories;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;
import java.time.OffsetDateTime;
import java.util.List;
import static ru.tinkoff.edu.java.scrapper.domain.jooq.Tables.LINKS;

@Repository
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
    public List<LinkEntity> findAll(Long chatId) {
        return dslContext.selectFrom(Links.LINKS)
                .where(Links.LINKS.ID_CHAT.eq(chatId.intValue()))
                .fetch()
                .map(record -> new LinkEntity(
                        record.getId(),
                        record.getUrl(),
                        record.getLastUpdateDate(),
                        record.getIdChat().longValue())
                );
    }

    @Transactional
    public List<LinkEntity> findAllOutdatedLinks(OffsetDateTime dateTime) {
        return dslContext.selectFrom(LINKS)
                .where(LINKS.LAST_UPDATE_DATE.lt(dateTime))
                .fetch()
                .map(record -> new LinkEntity(
                        record.getId(),
                        record.getUrl(),
                        record.getLastUpdateDate(),
                        record.getIdChat().longValue())
                );
    }
}
