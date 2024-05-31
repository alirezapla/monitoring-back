package com.example.monitor.management.domain.model;

public enum UnitType {
    MS("MS"), S("S"), M("M"), H("H");

    private final String title;

    UnitType(String title){
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
