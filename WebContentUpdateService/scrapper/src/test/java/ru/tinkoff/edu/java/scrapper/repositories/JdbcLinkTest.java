package ru.tinkoff.edu.java.scrapper.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dto.LinkEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(classes = ScrapperApplication.class)
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/scrapper",
        "spring.datasource.username=userDB",
        "spring.datasource.password=userDB_123",
        //"spring.liquibase.change-log=classpath:migrations/master.xml",
        "spring.liquibase.enabled=false"
})

public class JdbcLinkTest extends IntegrationEnvironment {
    @Autowired
    private LinksRepository linksRepository;
    @Autowired
    private ChatsRepository chatsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Long chatNumber = 7L;
        String userName = "user";
        chatsRepository.add(chatNumber, userName);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now(ZoneOffset.UTC);
        linksRepository.add(url, lastUpdateDate, chatNumber);

        List<LinkEntity> links = linksRepository.findAll(chatNumber);
        assertEquals(1, links.size());
        assertEquals(url, links.get(0).getUrl());
        assertEquals(chatNumber, links.get(0).getChatNumber());
        assertEquals(lastUpdateDate.toEpochSecond(),
                links.get(0).getLastUpdateDate().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Long chatNumber = 7L;
        String userName = "user";
        chatsRepository.add(chatNumber, userName);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now();
        linksRepository.add(url, lastUpdateDate, chatNumber);

        linksRepository.remove(url, chatNumber);
        List<LinkEntity> links = linksRepository.findAll(chatNumber);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        Long chatNumber = 7L;
        String userName = "user";
        chatsRepository.add(chatNumber, userName);

        String url1 = "https://www.testurl.com/1";
        String url2 = "https://www.testurl.com/2";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now();
        linksRepository.add(url1, lastUpdateDate, chatNumber);
        linksRepository.add(url2, lastUpdateDate, chatNumber);

        List<LinkEntity> links = linksRepository.findAll(chatNumber);
        assertEquals(2, links.size());
    }
}
