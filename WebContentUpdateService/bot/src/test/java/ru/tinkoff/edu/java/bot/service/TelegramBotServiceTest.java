package ru.tinkoff.edu.java.bot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.tinkoff.edu.java.bot.components.BotCommands;


@ExtendWith(MockitoExtension.class)
class TelegramBotServiceTest {
    @Spy
    TelegramBotService service;

    @Captor
    ArgumentCaptor<SendMessage> messageArgumentCaptor;

    @Test
    public void commandListTest_ListResult() {
        Update startMessage = getMessage("/start");
        long chatId = startMessage.getMessage().getChatId();
        String link = "https://github.com/sanyarnd/tinkoff-java-course-2022/";

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(Long.toString(chatId));
        expectedMessage.setText(link + "\n" + link);

        service.onUpdateReceived(startMessage);
        service.onUpdateReceived(getMessage("/track " + link));
        service.onUpdateReceived(getMessage("/track " + link));

        service.onUpdateReceived(getMessage("/list"));

        Mockito.verify(service, Mockito.times(4)).executeMessage(messageArgumentCaptor.capture());
        Assertions.assertEquals(messageArgumentCaptor.getAllValues().get(3), expectedMessage);
    }

    @Test
    public void commandListTest_EmptyResult() {
        Update update = getMessage("/list");
        long chatId = update.getMessage().getChat().getId();

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(Long.toString(chatId));
        expectedMessage.setText(BotCommands.USER_NOT_REGISTER);

        service.onUpdateReceived(update);

        Mockito.verify(service).executeMessage(messageArgumentCaptor.capture());
        Assertions.assertEquals(messageArgumentCaptor.getValue(), expectedMessage);
    }

    private Update getMessage(String command) {
        Chat chat = new Chat();
        chat.setId(3L);

        User user = new User();
        user.setId(15L);

        Message message = new Message();
        message.setChat(chat);
        message.setFrom(user);
        message.setText(command);

        Update update = new Update();
        update.setMessage(message);

        return update;
    }
}



