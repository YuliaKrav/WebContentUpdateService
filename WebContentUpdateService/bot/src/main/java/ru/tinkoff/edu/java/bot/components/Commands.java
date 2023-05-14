package ru.tinkoff.edu.java.bot.components;

public enum Commands {
    START("/start"),
    HELP("/help"),
    TRACK("/track"),
    UNTRACK("/untrack"),
    LIST("/list");

    private final String commandText;

    Commands(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }
}
