package com.example.monitor.management.common.exceptions;

public class BaseApplicationException extends RuntimeException {

    public BaseApplicationException(String message) {
        super(message);
    }

    public BaseApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
