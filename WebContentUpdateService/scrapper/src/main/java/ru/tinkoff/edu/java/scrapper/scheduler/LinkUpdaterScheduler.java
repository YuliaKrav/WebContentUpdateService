package ru.tinkoff.edu.java.scrapper.scheduler;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
import ru.tinkoff.edu.java.scrapper.clients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.clients.GitHubRepoResponse;
import ru.tinkoff.edu.java.scrapper.clients.StackOverflowClient;
import ru.tinkoff.edu.java.scrapper.clients.StackOverflowQuestionResponse;
import ru.tinkoff.edu.java.scrapper.constansts.InfoMessages;
import ru.tinkoff.edu.java.scrapper.dto.LinkDto;
import ru.tinkoff.edu.java.scrapper.services.BotService;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@Component
public class LinkUpdaterScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private final Counter messagesCounter;

    private final LinkService linkService;
    private final ChatService chatService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotService botService;

    @Value("${link.update.interval}")
    private String linkUpdateInterval;

    @Autowired
    public LinkUpdaterScheduler(MeterRegistry meterRegistry,
        LinkService linkService,
        ChatService chatService,
        GitHubClient gitHubClient,
        StackOverflowClient stackOverflowClient,
        BotService botService) {
        this.linkService = linkService;
        this.chatService = chatService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.botService = botService;

        this.messagesCounter = Counter.builder("scrapper_messages_counter")
            .description("The number of messages in scrapper application.")
            .register(meterRegistry);
    }

    @Scheduled(fixedDelayString = "#{@scheduledIntervalMs}")
    public void update() {
        LOGGER.info("Link update started...");

        OffsetDateTime currentTime = OffsetDateTime.now();
        OffsetDateTime outdatedTime = currentTime.minus(Duration.parse(linkUpdateInterval));
        List<LinkDto> outdatedLinksList = linkService.findAllOutdatedLinks(outdatedTime);
        LinkProcessingChain linkProcessingChain = new LinkProcessingChain();

        LOGGER.info(outdatedTime.toString());
        for (LinkDto outdatedLinkDTO : outdatedLinksList) {
            String updateDescription = "";
            String url = outdatedLinkDTO.getUrl();
            boolean isUpdated = false;
            LinkParserReplies urlType = linkProcessingChain.process(url);
            OffsetDateTime lastLinkUpdateDate = outdatedLinkDTO.getLastUpdateDate();

            switch (urlType) {
                case GitHubReply gitHubReply -> {
                    GitHubRepoResponse response = gitHubClient
                        .fetchRepoUpdates(gitHubReply.user(), gitHubReply.repository());

                    if (response.updatedAt().isAfter(lastLinkUpdateDate)) {
                        isUpdated = true;
                        updateDescription = InfoMessages.LINK_UPDATED;

                        if (response.pushedAt().isAfter(lastLinkUpdateDate)
                            && response.pushedAt().isBefore(currentTime)) {
                            updateDescription += InfoMessages.NEW_COMMENTS_ADDED;
                        }
                    }
                }
                case StackOverflowReply stackOverflowReply -> {
                    StackOverflowQuestionResponse currentResponse =
                        stackOverflowClient.fetchQuestionUpdates(Long.parseLong(stackOverflowReply.idRequest()));
                    StackOverflowQuestionResponse.Item currentResponseItem = currentResponse.items().get(0);

                    if (currentResponseItem.lastActivityDate().isAfter(outdatedTime)) {
                        isUpdated = true;
                        updateDescription = InfoMessages.LINK_UPDATED;
                        StackOverflowQuestionResponse outdatedResponse =
                            stackOverflowClient.fetchNewAnswers(
                                Long.parseLong(stackOverflowReply.idRequest()),
                                lastLinkUpdateDate
                            );
                        StackOverflowQuestionResponse.Item outdatedResponseItem = outdatedResponse.items().get(0);

                        if (currentResponseItem.answerCount() > outdatedResponseItem.answerCount()) {
                            updateDescription += InfoMessages.NEW_ANSWERS_ADDED;
                        }
                    }
                }
                default -> LOGGER.info("Unsupported url type: " + urlType.getClass().getSimpleName());
            }

            if (isUpdated) {
//                List<Long> tgChatsId = linkService.findAllChatsIdByUrl(url);
//
//                for (Long tgChatId : tgChatsId) {
//                    linkService.updateLastUpdateDate(url, tgChatId, currentTime);
//                }
                List<Long> tgChatsId = new ArrayList<>();
                tgChatsId.add(outdatedLinkDTO.getChatNumber());
                for (Long tgChatId : tgChatsId) {
                    linkService.updateLastUpdateDate(url, tgChatId, currentTime);
                }

                LinkUpdateRequest linkUpdateRequest =
                    new LinkUpdateRequest(
                        outdatedLinkDTO.getId(),
                        url,
                        updateDescription,
                        tgChatsId
                    );
                botService.sendUpdates(linkUpdateRequest);
                messagesCounter.increment();
                LOGGER.info("Current value of messagesCounter: {}", messagesCounter.count());
            }
        }
        botService.sendUpdates(
            new LinkUpdateRequest(1, "url", "description", List.of(743034562L)));
        messagesCounter.increment();
        LOGGER.info("Current value of messagesCounter: {}", messagesCounter.count());
    }
}
