package com.example.monitor.management.domain.model;

public enum IndicatorType {
    DYNAMIC_DATA("DYNAMIC_DATA"), KPI("KPI");

    private final String title;

    IndicatorType(String title) {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

}
