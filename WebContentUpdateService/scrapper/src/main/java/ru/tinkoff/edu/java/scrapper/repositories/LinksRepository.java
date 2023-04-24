package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class LinksRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(String url, OffsetDateTime lastUpdateDate, Long chatNumber) {
        String sql = "INSERT INTO public.links (url, last_update_date, id_chat) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, url, lastUpdateDate, chatNumber);
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
            linkEntity.setLastUpdateDate(rs.getObject("last_update_date",OffsetDateTime.class));
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
            linkEntity.setLastUpdateDate(rs.getObject("last_update_date",OffsetDateTime.class));
            linkEntity.setChatNumber(rs.getLong("id_chat"));
            return linkEntity;
        }, dateTime);
    }
}

