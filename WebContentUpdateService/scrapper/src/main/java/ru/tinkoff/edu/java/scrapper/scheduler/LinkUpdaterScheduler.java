package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parser.LinkProcessingChain;
import parser.replies.GitHubReply;
import parser.replies.LinkParserReplies;
import parser.replies.StackOverflowReply;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.clients.*;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.services.BotService;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.time.Duration;
import java.time.OffsetDateTime;
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
    private BotService botService;

    @Value("${link.update.interval}")
    private String linkUpdateInterval;

    @Scheduled(fixedDelayString = "#{@scheduledIntervalMs}")
    public void update() {
        LOGGER.info("Link update started...");

        OffsetDateTime currentTime = OffsetDateTime.now();
        OffsetDateTime outdatedTime = currentTime.minus(Duration.parse(linkUpdateInterval));
        List<LinkDto> outdatedLinksList = linkService.findAllOutdatedLinks(outdatedTime);
        LinkProcessingChain linkProcessingChain = new LinkProcessingChain();

        LOGGER.info(outdatedTime.toString());
        for (LinkDto linkDTO : outdatedLinksList) {
            String url = linkDTO.getUrl();
            boolean isUpdated = false;
            LinkParserReplies urlType = linkProcessingChain.process(url);

            switch (urlType) {
                case GitHubReply gitHubReply -> {
                    GitHubRepoResponse response = gitHubClient
                            .fetchRepoUpdates(gitHubReply.user(), gitHubReply.repository());
                    if (response.createdAt().isAfter(outdatedTime)) {
                        isUpdated = true;
                    }
                }
                case StackOverflowReply stackOverflowReply -> {
                    StackOverflowQuestionResponse response =
                            stackOverflowClient.fetchQuestionUpdates(Long.parseLong(stackOverflowReply.idRequest()));
                    for (StackOverflowQuestionResponse.Item item : response.items()) {
                        if (item.lastActivityDate().isAfter(outdatedTime)) {
                            isUpdated = true;
                            break;
                        }
                    }
                }
                default -> LOGGER.info("Unsupported url type: " + urlType.getClass().getSimpleName());
            }

            if (isUpdated) {
                List<Long> tgChatsId = linkService.findAllChatsIdByUrl(url);

                for (Long tgChatId : tgChatsId) {
                    linkService.updateLastUpdateDate(url, tgChatId, currentTime);
                }

                LinkUpdateRequest linkUpdateRequest =
                        new LinkUpdateRequest(
                                linkDTO.getId(),
                                url,
                                "updated",
                                tgChatsId);
                botService.sendUpdates(linkUpdateRequest);
            }
        }
//        botService.sendUpdates(
//                new LinkUpdateRequest(1, "url", "description", List.of(743034562L)));
    }
}
