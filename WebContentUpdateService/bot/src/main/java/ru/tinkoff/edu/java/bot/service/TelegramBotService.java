package ru.tinkoff.edu.java.bot.service;


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
import ru.tinkoff.edu.java.bot.clients.ListLinksResponse;
import ru.tinkoff.edu.java.bot.clients.ScrapperClient;
import ru.tinkoff.edu.java.bot.components.BotButtons;
import ru.tinkoff.edu.java.bot.components.BotCommands;
import ru.tinkoff.edu.java.bot.configuration.TelegramBotConfiguration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;

import java.util.*;
import java.util.stream.Collectors;

//@NoArgsConstructor(force = true) //for TelegramBotServiceTest
@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot implements BotCommands {
    //TODO вынести команды в enum с текстовым описанием
    private final Long CHAT_ID = 743034562L;
    private final Set<Long> allChatsId;
    private final String botToken;
    private final String botName;

    private final ScrapperClient scrapperClient;

    public TelegramBotService(TelegramBotConfiguration config, ScrapperClient scrapperClient) {
        this.botToken = config.getToken();
        this.botName = config.getName();
        this.scrapperClient = scrapperClient;
        this.allChatsId = new HashSet<>();

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
        sendText(CHAT_ID, linkUpdateRequest.toString());
    }

    private void botAnswerUtils(String receivedMessage, Message message) {
        if (receivedMessage.contains("/track") || receivedMessage.contains("/untrack")) {
            String[] splitString = receivedMessage.split("\\s+");
            if (splitString.length < 2) sendText(message.getChatId(), INVALID_FORMAT_TEXT);
            if (splitString[0].equals("/track")) trackLink(message, splitString[1]);
            if (splitString[0].equals("/untrack")) untrackLink(message, splitString[1]);

        } else {
            switch (receivedMessage) {
                case "/start" -> startBot(message.getChatId(), message.getFrom());
                case "/help" -> sendText(message.getChatId(), HELP_TEXT);
                case "/list" -> sendList(message);
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
                : listLinksResponse.links().stream()
                .map(LinkResponse::getUrl)
                .collect(Collectors.joining("\n"));
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