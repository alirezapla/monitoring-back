package com.example.monitor.management.api.utils.httputil.response;

public class SuccessfulRequestResponseEntity<T> extends BaseHttpResponse<T> {
    public SuccessfulRequestResponseEntity(T data) {
        super(data, System.currentTimeMillis(), "successfully done");
    }
}
