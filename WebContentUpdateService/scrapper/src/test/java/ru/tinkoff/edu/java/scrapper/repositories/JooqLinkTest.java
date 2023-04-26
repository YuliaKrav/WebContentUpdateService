package ru.tinkoff.edu.java.scrapper.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest(classes = ScrapperApplication.class)
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/scrapper",
        "spring.datasource.username=userDB",
        "spring.datasource.password=userDB_123",
        "spring.liquibase.enabled=false"
})
public class JooqLinkTest extends IntegrationEnvironment {
    @Autowired
    private JooqLinksRepository jooqLinksRepository;
    @Autowired
    private JooqChatsRepository jooqChatsRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Long chatNumber = 7L;
        String userName = "user";
        jooqChatsRepository.add(chatNumber, userName);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now(ZoneOffset.UTC);
        jooqLinksRepository.add(url, lastUpdateDate, chatNumber);

        List<LinkDto> links = jooqLinksRepository.findAll(chatNumber);
        assertEquals(1, links.size());
        assertEquals(url, links.get(0).getUrl());
        assertEquals(chatNumber, links.get(0).getChatNumber());
        assertEquals(lastUpdateDate.toEpochSecond(),
                links.get(0).getLastUpdateDate().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void addDuplicateTest() {
        Long chatNumber = 7L;
        String userName = "user";
        jooqChatsRepository.add(chatNumber, userName);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now(ZoneOffset.UTC);
        jooqLinksRepository.add(url, lastUpdateDate, chatNumber);

        assertThrows(DataIntegrityViolationException.class, () -> jooqLinksRepository.add(url, lastUpdateDate, chatNumber));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Long chatNumber = 7L;
        String userName = "user";
        jooqChatsRepository.add(chatNumber, userName);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now();
        jooqLinksRepository.add(url, lastUpdateDate, chatNumber);

        jooqLinksRepository.remove(url, chatNumber);
        List<LinkDto> links = jooqLinksRepository.findAll(chatNumber);
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        Long chatNumber = 7L;
        String userName = "user";
        jooqChatsRepository.add(chatNumber, userName);

        String url1 = "https://www.testurl.com/1";
        String url2 = "https://www.testurl.com/2";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now();
        jooqLinksRepository.add(url1, lastUpdateDate, chatNumber);
        jooqLinksRepository.add(url2, lastUpdateDate, chatNumber);

        List<LinkDto> links = jooqLinksRepository.findAll(chatNumber);
        assertEquals(2, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllOutdatedLinksTest() {
        Long chatNumber = 7L;
        String userName = "user";
        jooqChatsRepository.add(chatNumber, userName);

        String url1 = "https://www.testurl.com/1";
        String url2 = "https://www.testurl.com/2";
        OffsetDateTime lastUpdateDate1 = OffsetDateTime.now(ZoneOffset.UTC).minusDays(1); // outdated
        OffsetDateTime lastUpdateDate2 = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(3); // actual

        jooqLinksRepository.add(url1, lastUpdateDate1, chatNumber);
        jooqLinksRepository.add(url2, lastUpdateDate2, chatNumber);

        List<LinkDto> outdatedLinks = jooqLinksRepository.findAllOutdatedLinks(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(10));

        for (LinkDto linkDto : outdatedLinks) {
            System.out.println("Outdated link: " + linkDto.getUrl());
        }
        assertEquals(1, outdatedLinks.size());
        assertEquals(url1, outdatedLinks.get(0).getUrl());
        assertEquals(chatNumber, outdatedLinks.get(0).getChatNumber());
    }
}