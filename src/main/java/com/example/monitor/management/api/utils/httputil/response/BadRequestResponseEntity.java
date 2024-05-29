package com.example.monitor.management.api.utils.httputil.response;


import com.example.monitor.management.common.exceptions.ExceptionMessages;

public class BadRequestResponseEntity<T> extends BaseHttpResponse<T> {
    public BadRequestResponseEntity(T data) {
        super(data, System.currentTimeMillis(), ExceptionMessages.BAD_REQUEST.getTitle());
    }
}
