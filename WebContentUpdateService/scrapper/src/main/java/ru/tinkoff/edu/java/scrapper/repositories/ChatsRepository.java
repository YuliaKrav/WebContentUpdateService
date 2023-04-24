package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.ChatEntity;

import java.util.List;

@Repository
public class ChatsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(Long chatNumber, String userName) {
        String sql = "INSERT INTO public.chats (chat_number, user_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, chatNumber, userName);
    }

    @Transactional
    public void remove(Long chatNumber) {
        String sql = "DELETE FROM public.chats WHERE chat_number = ?";
        jdbcTemplate.update(sql, chatNumber);
    }

    @Transactional
    public List<ChatEntity> findAll() {
        String sql = "SELECT chat_number, user_name FROM public.chats";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ChatEntity(rs.getLong(1), rs.getString(2)));
    }
}
