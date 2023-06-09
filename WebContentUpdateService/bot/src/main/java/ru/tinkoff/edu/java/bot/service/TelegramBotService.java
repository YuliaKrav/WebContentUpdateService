package ru.tinkoff.edu.java.bot.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.edu.java.bot.clients.LinkResponse;
import ru.tinkoff.edu.java.bot.clients.ListLinksResponse;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.components.BotButtons;
import ru.tinkoff.edu.java.bot.components.BotCommands;
import ru.tinkoff.edu.java.bot.components.Commands;
import ru.tinkoff.edu.java.bot.configuration.TelegramBotConfiguration;
import ru.tinkoff.edu.java.dto.LinkUpdateRequest;

//@NoArgsConstructor(force = true) //for TelegramBotServiceTest
@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot implements BotCommands {
    //TODO вынести команды в enum с текстовым описанием
    private final Set<Long> allChatsId;
    private final String botToken;
    private final String botName;

    private final ScrapperClient scrapperClient;
    private final Counter messagesCounter;

    public TelegramBotService(
        TelegramBotConfiguration config,
        ScrapperClient scrapperClient,
        MeterRegistry meterRegistry
    ) {
        this.botToken = config.getToken();
        this.botName = config.getName();
        this.scrapperClient = scrapperClient;
        this.allChatsId = new HashSet<>();
        this.messagesCounter = Counter.builder("bot_messages_counter")
            .description("The number of messages in bot application.")
            .register(meterRegistry);

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        //long chatId = 0;
        String receivedMessage;
        messagesCounter.increment();
        log.info("Current value of messagesCounter: {}", messagesCounter.count());

        if (update.hasMessage()) {
            // chatId = update.getMessage().getChatId();

            if (update.getMessage().hasText()) { //text message
                receivedMessage = update.getMessage().getText();
                botAnswerUtils(receivedMessage, update.getMessage());
            }

        } else if (update.hasCallbackQuery()) { //button click
            // chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswerUtils(receivedMessage, update.getCallbackQuery().getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendNotificationToUser(LinkUpdateRequest linkUpdateRequest) {
        String message =
            String.format(
                "Произошли изменения по ссылке '%s': %s",
                linkUpdateRequest.getUrl(),
                linkUpdateRequest.getDescription()
            );
        for (Long tgChatId : linkUpdateRequest.getTgChatIds()) {
            sendText(tgChatId, message);
        }
    }

    private void botAnswerUtils(String receivedMessage, Message message) {
        Commands command = Arrays.stream(Commands.values())
            .filter(cmd -> receivedMessage.startsWith(cmd.getCommandText()))
            .findFirst()
            .orElse(null);

        if (command == null) {
            sendText(message.getChatId(), UNKNOWN_COMMAND);
            return;
        }

        if (command == Commands.TRACK || command == Commands.UNTRACK) {
            String[] splitString = receivedMessage.split("\\s+");
            if (splitString.length < 2) {
                sendText(message.getChatId(), INVALID_FORMAT_TEXT);
            }
            if (command == Commands.TRACK) {
                trackLink(message, splitString[1]);
            }
            if (command == Commands.UNTRACK) {
                untrackLink(message, splitString[1]);
            }

        } else {
            switch (command) {
                case START -> startBot(message.getChatId(), message.getFrom());
                case HELP -> sendText(message.getChatId(), HELP_TEXT);
                case LIST -> sendList(message);
                default -> sendText(message.getChatId(), UNKNOWN_COMMAND);
            }
        }
    }

    private void startBot(long chatId, User user) {
        scrapperClient.registerChat(chatId);
        allChatsId.add(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chatId));
        message.setText("Привет, " + user.getFirstName()
            + "! Я бот, " + chatId + " который поможет тебе отслеживать ссылки.'");
        message.setReplyMarkup(BotButtons.inlineMarkup());
        executeMessage(message);
        scrapperClient.registerChat(chatId);
    }

    void executeMessage(SendMessage message) {
        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendText(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(Long.toString(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    private void sendList(Message message) {
        if (allChatsId.contains(message.getChatId())) {
            ListLinksResponse listLinksResponse = scrapperClient.getAllLinks(message.getChatId());
            SendMessage send = new SendMessage();
            send.setChatId(Long.toString(message.getChatId()));
            send.setText(getAllLinksStringFromResponse(listLinksResponse));
            executeMessage(send);
        } else {
            sendText(message.getChatId(), USER_NOT_REGISTER);
            log.warn(String.format("User with id %d not register", message.getFrom().getId()));
        }
    }

    private String getAllLinksStringFromResponse(ListLinksResponse listLinksResponse) {
        return listLinksResponse.links().size() == 0
            ? EMPTY_LIST
            : listLinksResponse.links().stream().map(LinkResponse::getUrl).collect(Collectors.joining("\n"));
    }

    void trackLink(Message message, String link) {
        if (allChatsId.contains(message.getChatId())) {
            scrapperClient.addLink(message.getChatId(), link);
            sendText(message.getChatId(), String.format("Ссылка %s успешно сохранена", link));
        } else {
            sendText(message.getChatId(), USER_NOT_REGISTER);
        }
    }

    private void untrackLink(Message message, String link) {
        if (allChatsId.contains(message.getChatId())) {
            scrapperClient.removeLink(message.getChatId(), link);
            sendText(message.getChatId(), String.format("Ссылка %s успешно удалена", link));
        } else {
            sendText(message.getChatId(), USER_NOT_REGISTER);
        }
    }
}
