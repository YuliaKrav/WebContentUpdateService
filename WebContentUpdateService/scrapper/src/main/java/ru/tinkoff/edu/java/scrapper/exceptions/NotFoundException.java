package ru.tinkoff.edu.java.scrapper.exceptions;

public class NotFoundException extends ApiException {
    private final int code;

    public NotFoundException(int code, String msg) {
        super(code, msg);
        this.code = code;
    }
}
