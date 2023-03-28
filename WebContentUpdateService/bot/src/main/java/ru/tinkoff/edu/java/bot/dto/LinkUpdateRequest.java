package ru.tinkoff.edu.java.bot.dto;

import java.util.List;

public final class LinkUpdateRequest {
    private Integer id;

    private String url;

    private String description;

    private List<Integer> tgChatIds;

    public LinkUpdateRequest() {
    }

    public LinkUpdateRequest(int id, String url, String description,  List<Integer> tgChatIds) {
        this.id = id;
        this.url = url;
        this.description = description;
        this.tgChatIds = tgChatIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public  List<Integer> getTgChatIds() {
        return tgChatIds;
    }

    public void setTgChatIds( List<Integer> tgChatIds) {
        this.tgChatIds = tgChatIds;
    }
}


//
//
//        import org.springframework.http.HttpStatus;
//        import org.springframework.http.ResponseEntity;
//        import org.springframework.validation.FieldError;
//        import org.springframework.web.bind.MethodArgumentNotValidException;
//        import org.springframework.web.bind.annotation.ExceptionHandler;
//        import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//        import java.util.ArrayList;
//        import java.util.List;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//        List<String> errors = new ArrayList<>();
//        for (FieldError error : ex.getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        ApiErrorResponse errorResponse = new ApiErrorResponse(
//                "Некорректные параметры запроса",
//                "400",
//                ex.getClass().getSimpleName(),
//                ex.getMessage(),
//                errors
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
//        ApiErrorResponse errorResponse = new ApiErrorResponse(
//                "Ошибка сервера",
//                "500",
//                ex.getClass().getSimpleName(),
//                ex.getMessage(),
//                null
//        );
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
//
//
//
//
////
//
//public class ApiErrorResponse {
//    private String description;
//    private String code;
//    private String exceptionName;
//    private String exceptionMessage;
//    private List<String> stacktrace;
//
//    // jackson
//    public ApiErrorResponse() {}
//
//    public ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage, List<String> stacktrace) {
//        this.description = description;
//        this.code = code;
//        this.exceptionName = exceptionName;
//        this.exceptionMessage = exceptionMessage;
//        this.stacktrace = stacktrace;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getExceptionName() {
//        return exceptionName;
//    }
//
//import requests
//
//            url = "http://localhost:8080/api/v1/updates"
//    headers = {'Content-Type': 'application/json'}
//    data = {
//        "id": 0,
//                "url": "https://example.com",
//                "description": "Example description",
//                "tgChatIds": [0]
//    }
//    response = requests.post(url, headers=headers, json=data)
//
//            if response.status_code == 200:
//    print("Request succeeded")
//else:
//    print(f"Request failed with status code {response.status_code}")
//
//
//
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.ArrayList;
//import java.util.List;
//
//    @RestControllerAdvice
//    public class GlobalExceptionHandler {
//
//        @ExceptionHandler(MethodArgumentNotValidException.class)
//        public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
//            List<String> errors = new ArrayList<>();
//            for (FieldError error : ex.getFieldErrors()) {
//                errors.add(error.getField() + ": " + error.getDefaultMessage());
//            }
//            ApiErrorResponse errorResponse = new ApiErrorResponse(
//                    "Некорректные параметры запроса",
//                    "400",
//                    ex.getClass().getSimpleName(),
//                    ex.getMessage(),
//                    errors
//            );
//            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//        }
//
//        @ExceptionHandler(Exception.class)
//        public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
//            ApiErrorResponse errorResponse = new ApiErrorResponse(
//                    "Ошибка сервера",
//                    "500",
//                    ex.getClass().getSimpleName(),
//                    ex.getMessage(),
//                    null
//            );
//            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//
//
//
//    public class ApiErrorResponse {
//        private String description;
//        private String code;
//        private String exceptionName;
//        private String exceptionMessage;
//        private List<String> stacktrace;
//
//        // jackson
//        public ApiErrorResponse() {}
//
//        public ApiErrorResponse(String description, String code, String exceptionName, String exceptionMessage, List<String> stacktrace) {
//            this.description = description;
//            this.code = code;
//            this.exceptionName = exceptionName;
//            this.exceptionMessage = exceptionMessage;
//            this.stacktrace = stacktrace;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getExceptionName() {
//            return exceptionName;
//        }
//    }








