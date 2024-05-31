package com.example.monitor.management.common.exceptions;


public class BodyValidationException extends RuntimeException {

    public BodyValidationException(String messageKey) {
        super(messageKey);
    }
}
