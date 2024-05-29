package com.example.monitor.management.api.utils.httputil.response;


import com.example.monitor.management.common.exceptions.ExceptionMessages;

public class ServiceUnavailableResponseEntity<T> extends BaseHttpResponse<T> {
    public ServiceUnavailableResponseEntity(T data) {
        super(data, System.currentTimeMillis(), ExceptionMessages.EXCEPTION.getTitle());
    }
}
