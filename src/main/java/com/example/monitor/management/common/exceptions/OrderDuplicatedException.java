package com.example.monitor.management.common.exceptions;

public class OrderDuplicatedException extends RuntimeException {
    public OrderDuplicatedException(String message) {
        super(message);
    }

}
