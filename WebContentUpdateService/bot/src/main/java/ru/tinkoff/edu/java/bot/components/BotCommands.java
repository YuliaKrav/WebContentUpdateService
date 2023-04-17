package ru.tinkoff.edu.java.bot.components;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface BotCommands {
    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info"),
            new BotCommand("/list", "link list"),
            new BotCommand("/track", "track link"),
            new BotCommand("/untrack", "untrack link")
    );

    String HELP_TEXT = """
            Этот бот поможет вам отслеживать обновления интересующих вас ссылок
            Нажмите/введите /start чтобы зарегистрироваться
            Нажмите/введите /help для получения помощи
            Нажмите/введите /list для получения список ссылок на отслеживании
            Введите /track <url> чтобы добавить ссылку на отслеживание
            Введите /untrack <url> чтобы убрать ссылку с отслеживания
            """;

    String INVALID_FORMAT_TEXT = """
            Сообщения нужно указывать в формате /track <url> или /untrack <url>
            """;

    String EMPTY_LIST = """
            Список ссылок на отслеживании пуст
            """;

    String USER_NOT_REGISTER = """
            Пользователь не зарегистрирован
            """;

    String UNKNOWN_COMMAND = """
            Неизвестная команда, ознакомтесь со список команд вызвав /help
            """;
}
