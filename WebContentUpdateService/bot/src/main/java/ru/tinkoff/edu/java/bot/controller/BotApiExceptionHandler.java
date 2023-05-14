package ru.tinkoff.edu.java.bot.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;

@RestControllerAdvice
public class BotApiExceptionHandler {
    //TODO При желании можете дополнить API обработкой исключительных ситуаций, таких как повторная регистрация,
    // повторное добавление ссылки или отсутствие чата.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Некорректные параметры запроса",
            "400",
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Ошибка сервера",
            "500",
            ex.getClass().getSimpleName(),
            ex.getMessage(),
            null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
