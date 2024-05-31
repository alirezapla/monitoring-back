package com.example.monitor.management.common.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComputingTableItemsDto {
    private String id;
    private String description;
    private String command;
    private boolean isHided;

    public ComputingTableItemsDto getObject(){
        return this;
    }
}
