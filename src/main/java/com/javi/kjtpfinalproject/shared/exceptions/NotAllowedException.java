package com.javi.kjtpfinalproject.shared.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class NotAllowedException extends RuntimeException {
    public NotAllowedException() {
    }

    public NotAllowedException(String message) {
        super(message);
    }

    public NotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedException(Throwable cause) {
        super(cause);
    }

    public NotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
