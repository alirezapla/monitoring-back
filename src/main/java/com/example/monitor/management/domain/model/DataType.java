package com.example.monitor.management.domain.model;

public enum DataType {

    STRING("STRING"), INT("INT"), DATETIME("DATETIME"), ENUM("ENUM"), FLOAT("FLOAT");

    private final String title;

    DataType(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}
