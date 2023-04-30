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
import ru.tinkoff.edu.java.scrapper.dto.ChatDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinksResponse;
import ru.tinkoff.edu.java.scrapper.entity.ChatEntity;
import ru.tinkoff.edu.java.scrapper.entity.LinkEntity;
import ru.tinkoff.edu.java.scrapper.services.JpaChatService;
import ru.tinkoff.edu.java.scrapper.services.JpaLinkService;

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
public class JpaLinkTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinksRepository jpaLinksRepository;

    @Autowired
    private JpaChatsRepository jpaChatsRepository;

    @Autowired
    private JpaChatService jpaChatService;

    @Autowired
    private JpaLinkService jpaLinkService;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Long chatNumber = 7L;
        //String userName = "user";
        jpaChatService.add(chatNumber);

        String url = "https://www.testurl.com";
        OffsetDateTime lastUpdateDate = OffsetDateTime.now(ZoneOffset.UTC);

        jpaLinkService.add(url, chatNumber);

        ListLinksResponse listLinksResponse = jpaLinkService.findAll(chatNumber);
        List<LinkResponse> links = listLinksResponse.getLinks();
        assertEquals(1, links.size());
        assertEquals(url, links.get(0).getUrl());

        ChatDto chatDto = jpaChatService.findByChatNumber(chatNumber);
        LinkDto linkDto = jpaLinkService.findByUrl(url);
        assertEquals(chatNumber, chatDto.getChatNumber());
        assertEquals(lastUpdateDate.toEpochSecond(), linkDto.getLastUpdateDate().toEpochSecond());
    }

    @Test
    @Transactional
    @Rollback
    public void addDuplicateTest() {
        Long chatNumber = 7L;
        jpaChatService.add(chatNumber);

        String url = "https://www.testurl.com";

        jpaLinkService.add(url, chatNumber);
        assertThrows(DataIntegrityViolationException.class, () -> jpaLinkService.add(url, chatNumber));
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Long chatNumber = 7L;
        jpaChatService.add(chatNumber);

        String url = "https://www.testurl.com";
        jpaLinkService.add(url, chatNumber);

        jpaLinkService.remove(url, chatNumber);
        ListLinksResponse listLinksResponse = jpaLinkService.findAll(chatNumber);
        List<LinkResponse> links = listLinksResponse.getLinks();
        assertTrue(links.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {
        Long chatNumber = 7L;
        jpaChatService.add(chatNumber);

        String url1 = "https://www.testurl.com/1";
        String url2 = "https://www.testurl.com/2";
        jpaLinkService.add(url1, chatNumber);
        jpaLinkService.add(url2, chatNumber);

        Long chatNumber2 = 9L;
        jpaChatService.add(chatNumber2);
        jpaLinkService.add(url1, chatNumber2);

        ListLinksResponse listLinksResponse = jpaLinkService.findAll(chatNumber);
        List<LinkResponse> links = listLinksResponse.getLinks();
        assertEquals(2, links.size());
    }

    @Test
    @Transactional
    @Rollback
    public void findAllOutdatedLinksTest() {
        Long chatNumber = 7L;
        jpaChatService.add(chatNumber);

        String url1 = "https://www.testurl.com/1";
        String url2 = "https://www.testurl.com/2";
        OffsetDateTime lastUpdateDate1 = OffsetDateTime.now(ZoneOffset.UTC).minusDays(1); // outdated
        OffsetDateTime lastUpdateDate2 = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(3); // actual

        LinkEntity linkEntity1 = LinkEntity.builder()
                .url(url1)
                .lastUpdateDate(lastUpdateDate1)
                .chatEntity(ChatEntity.builder().chatNumber(chatNumber).build())
                .build();
        LinkEntity linkEntity2 = LinkEntity.builder()
                .url(url2)
                .lastUpdateDate(lastUpdateDate2)
                .chatEntity(ChatEntity.builder().chatNumber(chatNumber).build())
                .build();
        jpaLinksRepository.save(linkEntity1);
        jpaLinksRepository.save(linkEntity2);

        List<LinkDto> outdatedLinks = jpaLinkService.findAllOutdatedLinks(OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(10));
        assertEquals(1, outdatedLinks.size());
        assertEquals(url1, outdatedLinks.get(0).getUrl());
        assertEquals(chatNumber, outdatedLinks.get(0).getChatNumber());
    }
}
