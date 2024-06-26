package com.example.monitor.management.common;

public enum AppLogEvent {

    CREATE_REQUEST_RECEIVED,
    FETCH_REQUEST_RECEIVED,
    EDIT_REQUEST_RECEIVED,
    DELETE_REQUEST_RECEIVED,
    LOGIN_REQUEST_RECEIVED,
    CREATE_RESPONSE_SENT,
    FETCH_RESPONSE_SENT,
    EDIT_RESPONSE_SENT,
    DELETE_RESPONSE_SENT,
    LOGIN_RESPONSE_SENT,
    LOGIN_SERVICE_STARTED;


    @Override
    public String toString() {
        return this.name();
    }
}
