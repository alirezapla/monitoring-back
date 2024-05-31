package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DocTableDto {
    private String id;
    //    @NotNull(message = "The name is required.")
    private String name;
    //    @Valid
    @JsonProperty("indicators")
    private List<IndicatorDto> indicators;

    private boolean isHided;

    public DocTableDto getObject() {
        return this;
    }


}
