package ru.tinkoff.edu.java.bot.components;

import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class BotButtons {
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton LIST_BUTTON = new InlineKeyboardButton("List");

    private BotButtons() {

    }

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");
        LIST_BUTTON.setCallbackData("/list");

        List<InlineKeyboardButton> rowLine = List.of(START_BUTTON, HELP_BUTTON, LIST_BUTTON);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowLine);

        InlineKeyboardMarkup markupLine = new InlineKeyboardMarkup();
        markupLine.setKeyboard(rowsInLine);

        return markupLine;
    }
}
