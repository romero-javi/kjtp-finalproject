package com.javi.kjtpfinalproject.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class NextHandlerNotSetException extends RuntimeException {
    public NextHandlerNotSetException() {
        super();
    }

    public NextHandlerNotSetException(String message) {
        super(message);
    }

    public NextHandlerNotSetException(String message, Throwable cause) {
        super(message, cause);
    }

    public NextHandlerNotSetException(Throwable cause) {
        super(cause);
    }

    protected NextHandlerNotSetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
