package com.example.monitor.management.common.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CreateBodyDto {
    @NotNull(message = "The name is required.")
    private String name;
    private String description;

}
