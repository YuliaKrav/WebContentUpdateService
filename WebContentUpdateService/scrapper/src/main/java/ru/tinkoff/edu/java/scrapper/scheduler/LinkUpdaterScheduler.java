package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.clients.*;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LinkUpdaterScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkUpdaterScheduler.class);

    @Autowired
    private LinkService linkService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverflowClient stackOverflowClient;

    @Autowired
    private BotClient botClient;

    @Value("${link.update.interval}")
    private String linkUpdateInterval;

    @Scheduled(fixedDelayString = "#{@scheduledIntervalMs}")
    public void update() {
        LOGGER.info("Link update started...");

        OffsetDateTime outdatedTime = OffsetDateTime.now().minus(Duration.parse(linkUpdateInterval));
        List<LinkDto> outdatedLinksList = linkService.findAllOutdatedLinks(outdatedTime);

        LOGGER.info(outdatedTime.toString());
        for (LinkDto linkDTO : outdatedLinksList) {
            String url = linkDTO.getUrl();
            boolean isUpdated = false;

            if (url.contains("github.com")) {
                String[] splitUrl = url.split("/");
                String user = splitUrl[3];
                String repo = splitUrl[4];
                GitHubRepoResponse response = gitHubClient.fetchRepoUpdates(user, repo);
                if (response.createdAt().isAfter(outdatedTime)) {
                    isUpdated = true;
                }
            } else if (url.contains("stackoverflow.com")) {
                String[] splitUrl = url.split("/");
                Long questionId = Long.parseLong(splitUrl[4]);
                StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestionUpdates(questionId);
                for (StackOverflowQuestionResponse.Item item : response.items()) {
                    if (item.lastActivityDate().isAfter(outdatedTime)) {
                        isUpdated = true;
                        break;
                    }
                }
            }

            if (isUpdated) {
                List<Long> tgChatsId = new ArrayList<>();
                tgChatsId.add(linkDTO.getChatNumber());
                LinkUpdateRequest linkUpdateRequest =
                        new LinkUpdateRequest(
                                linkDTO.getId(),
                                url,
                                "updated",
                                tgChatsId);
                botClient.postUpdates(linkUpdateRequest);
            }
        }
    }
}
