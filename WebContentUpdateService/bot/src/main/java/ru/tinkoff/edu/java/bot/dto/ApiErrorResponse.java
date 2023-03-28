package ru.tinkoff.edu.java.bot.dto;

import java.util.List;

public class ApiErrorResponse {
    private String description;
    private String code;
    private String exceptionName;
    private String exceptionMessage;
    private List<String> stacktrace;

    // jackson
    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage, List<String> stacktrace) {
        this.description = description;
        this.code = code;
        this.exceptionName = exceptionName;
        this.exceptionMessage = exceptionMessage;
        this.stacktrace = stacktrace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExceptionName() {
        return exceptionName;
    }
}
