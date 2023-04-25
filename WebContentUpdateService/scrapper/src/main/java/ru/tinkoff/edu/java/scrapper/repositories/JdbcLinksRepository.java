package ru.tinkoff.edu.java.scrapper.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class JdbcLinksRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplate.class);

    @Autowired
    public JdbcLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(String url, OffsetDateTime lastUpdateDate, Long chatNumber) {
        //TODO добавить сообщениедля пользователя о дублировании ссылки для отслеживания
        String sql = "INSERT INTO public.links (url, last_update_date, id_chat) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, url, lastUpdateDate, chatNumber);
        } catch (DuplicateKeyException ex) {
            LOGGER.info("This link is already tracked");
        }
    }

    @Transactional
    public void remove(String url, Long chatNumber) {
        String sql = "DELETE FROM public.links WHERE url = ? AND id_chat = ?";
        jdbcTemplate.update(sql, url, chatNumber);
    }

    @Transactional
    public List<LinkEntity> findAll(Long chatId) {
        String sql = "SELECT id, url, last_update_date, id_chat FROM public.links WHERE id_chat = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LinkEntity linkEntity = new LinkEntity();
            linkEntity.setId(rs.getInt("id"));
            linkEntity.setUrl(rs.getString("url"));
            linkEntity.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
            linkEntity.setChatNumber(rs.getLong("id_chat"));
            return linkEntity;
        }, chatId);
    }

    @Transactional
    public List<LinkEntity> findAllOutdatedLinks(OffsetDateTime dateTime) {
        String sql = "SELECT id, url, last_update_date, id_chat FROM public.links WHERE last_update_date < ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LinkEntity linkEntity = new LinkEntity();
            linkEntity.setId(rs.getInt("id"));
            linkEntity.setUrl(rs.getString("url"));
            linkEntity.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
            linkEntity.setChatNumber(rs.getLong("id_chat"));
            return linkEntity;
        }, dateTime);
    }
}

