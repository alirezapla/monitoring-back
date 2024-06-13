package com.example.monitor.management.common.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IndicatorComputationDto {
    private String id;
    private String label;
    private String description;


    public IndicatorComputationDto getObject() {
        return this;
    }

    public String getId() {
        if (this.id == null) {
            return UUID.randomUUID().toString();
        }
        return this.id;
    }

}
