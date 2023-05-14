package ru.tinkoff.edu.java.scrapper.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;

public class JdbcChatsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcChatsRepository(JdbcTemplate jdbcTemplate) {
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
    public List<ChatDto> findAll() {
        String sql = "SELECT chat_number, user_name FROM public.chats";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new ChatDto(rs.getLong(1), rs.getString(2))
        );
    }
}
