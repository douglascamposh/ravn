package com.ravn.movies.error;

public class DuplicatedDataException extends RuntimeException {
    public DuplicatedDataException(String message) {
        super(message);
    }
    public DuplicatedDataException(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicatedDataException(Throwable cause) {
        super(cause);
    }
    public DuplicatedDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public DuplicatedDataException() {
    }
}
