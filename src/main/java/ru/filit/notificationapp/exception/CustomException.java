package ru.filit.notificationapp.exception;

import ru.filit.notificationapp.type.StatusCode;

public class CustomException extends RuntimeException {
    public StatusCode status;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, StatusCode statusCode) {
        super(message);
        this.status = statusCode;
    }
}
