package ru.tinkoff.edu.java.scrapper.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;

import java.time.OffsetDateTime;
import java.util.List;


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

    public List<LinkDto> findAll() {
        String sql = "SELECT id, url, last_update_date, id_chat FROM public.links";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LinkDto linkDTO = new LinkDto();
            linkDTO.setId(rs.getInt("id"));
            linkDTO.setUrl(rs.getString("url"));
            linkDTO.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
            linkDTO.setChatNumber(rs.getLong("id_chat"));
            return linkDTO;
        });
    }

    //@Transactional
    public List<LinkDto> findAll(Long chatId) {
        String sql = "SELECT id, url, last_update_date, id_chat FROM public.links WHERE id_chat = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LinkDto linkDTO = new LinkDto();
            linkDTO.setId(rs.getInt("id"));
            linkDTO.setUrl(rs.getString("url"));
            linkDTO.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
            linkDTO.setChatNumber(rs.getLong("id_chat"));
            return linkDTO;
        }, chatId);
    }

    //@Transactional
    public List<LinkDto> findAllOutdatedLinks(OffsetDateTime dateTime) {
        String sql = "SELECT id, url, last_update_date, id_chat FROM public.links WHERE last_update_date < ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            LinkDto linkDTO = new LinkDto();
            linkDTO.setId(rs.getInt("id"));
            linkDTO.setUrl(rs.getString("url"));
            linkDTO.setLastUpdateDate(rs.getObject("last_update_date", OffsetDateTime.class));
            linkDTO.setChatNumber(rs.getLong("id_chat"));
            return linkDTO;
        }, dateTime);
    }

    //@Transactional
    public List<Long> findAllChatsIdByUrl(String url) {
        String sql = "SELECT id_chat FROM links WHERE url = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id_chat"), url);
    }
}

