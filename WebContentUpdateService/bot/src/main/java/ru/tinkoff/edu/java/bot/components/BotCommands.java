package ru.tinkoff.edu.java.bot.components;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

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
        Нажмите/введите /list для получения списка отслеживаемых ссылок
        Введите /track <url> чтобы добавить оссылку с список отслеживаемых
        Введите /untrack <url> чтобы убрать ссылку из списка отслеживаемых
        """;

    String INVALID_FORMAT_TEXT = """
        Сообщения нужно указывать в формате /track <url> или /untrack <url>
        """;

    String EMPTY_LIST = """
        Список отслеживаемых ссылок пуст
        """;

    String USER_NOT_REGISTER = """
        Вы не зарегистрированы. Пожалуйста, начните с комманды /start
        """;

    String UNKNOWN_COMMAND = """
        Неизвестная команда, ознакомтесь со список команд вызвав /help
        """;
}
