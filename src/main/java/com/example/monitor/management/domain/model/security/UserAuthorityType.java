package com.example.monitor.management.domain.model.security;


import com.example.monitor.management.common.exceptions.BaseApplicationException;
import com.example.monitor.management.common.exceptions.ExceptionMessages;

public enum UserAuthorityType {
    AUTHORITY_ADMIN(0, "AUTHORITY_ADMIN");
    private final int index;
    private final String title;

    UserAuthorityType(int index, String title) {
        this.index = index;
        this.title = title;
    }

    public int getIndex() {
        return index;
    }

    public String getTitle() {
        return title;
    }


    public static UserAuthorityType getByUserAuthorityTitle(String userAuthorityTypeTitle) {
        for (UserAuthorityType userAuthorityType : UserAuthorityType.values()) {
            if (userAuthorityType.getTitle().equals(userAuthorityTypeTitle))
                return userAuthorityType;
        }
        throw new BaseApplicationException(ExceptionMessages.EXCEPTION.getTitle());
    }

    @Override
    public String toString() {
        return "UserAuthorityType{" +
                "index=" + index +
                ", title='" + title + '\'' +
                '}';
    }
}
