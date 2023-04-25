package ru.tinkoff.edu.java.scrapper.repositories;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;
import ru.tinkoff.edu.java.scrapper.ScrapperApplication;
import ru.tinkoff.edu.java.scrapper.services.JpaChatService;
import ru.tinkoff.edu.java.scrapper.services.JpaLinkService;

@SpringBootTest(classes = ScrapperApplication.class)
//@DataJpaTest
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

    private JpaLinkService jpaLinkService;

    private JpaChatService jpaChatService;

    @Before
    public void setUp() {
        jpaLinkService = new JpaLinkService(jpaLinksRepository);
        jpaChatService = new JpaChatService(jpaChatsRepository);
    }


    @Test
    @Transactional
    @Rollback
    public void addTest() {

    }

    @Test
    @Transactional
    @Rollback
    public void addDuplicateTest() {
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
    }

    @Test
    @Transactional
    @Rollback
    public void findAllTest() {

    }
}
