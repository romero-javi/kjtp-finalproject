package com.javi.kjtpfinalproject.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUUID extends RuntimeException{
    public InvalidUUID() {
    }

    public InvalidUUID(String message) {
        super(message);
    }

    public InvalidUUID(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUUID(Throwable cause) {
        super(cause);
    }

    public InvalidUUID(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
