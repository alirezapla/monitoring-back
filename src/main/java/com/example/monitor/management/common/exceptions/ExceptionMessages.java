package com.example.monitor.management.common.exceptions;

public enum ExceptionMessages {
    EXCEPTION(0, "Exception!"),
    BAD_REQUEST(1, "Bad request!"),
    USER_CREDENTIAL_NOT_FOUND(2, "UserCredential for requested username not found."),
    PASSWORD_IS_NOT_VALID(3, "Entered password is not valid."),
    RECORD_NOT_FOUND(4, "Record not found."),
    CURRENT_PASSWORD_IS_EQUAL_TO_NEW_PASSWORD(5, "Current password is equal to new password!"),
    ILLEGAL_STATE(6, "Illegal state!"),
    SERVICE_UNAVAILABLE(7, "Service Unavailable");

    private int index;
    private String title;

    ExceptionMessages(int index, String title) {
        this.index = index;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public String toString() {
        return "ExceptionMessages{" +
                "index=" + index +
                ", title='" + title + '\'' +
                '}';
    }
}
