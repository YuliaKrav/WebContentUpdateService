package ru.tinkoff.edu.java.scrapper.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dto.ChatEntity;

import java.util.List;

@Repository
public class ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(Long chatNumber) {
        String sql = "INSERT INTO public.chats (chat_number, user_name) VALUES (?, NULL)";
        jdbcTemplate.update(sql, chatNumber);
    }

    public void remove(Long chatNumber) {
        String sql = "DELETE FROM public.chats WHERE chat_number = ?";
        jdbcTemplate.update(sql, chatNumber);
    }

    public List<ChatEntity> findAll() {
        String sql = "SELECT chat_number, user_name FROM public.chats";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ChatEntity(rs.getInt(1), rs.getString(2)));
    }
}
