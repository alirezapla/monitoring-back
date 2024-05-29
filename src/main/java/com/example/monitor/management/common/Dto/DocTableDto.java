package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class DocTableDto {
//    @NotNull(message = "The name is required.")
    private String name;
//    @Valid
    @JsonProperty("indicators")
    private List<IndicatorDto> indicators;

}
