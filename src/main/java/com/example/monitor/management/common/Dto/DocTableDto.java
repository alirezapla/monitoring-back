package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DocTableDto {
    private String id;

    @NotNull(message = "The name is required.")
    private String name;

    @Valid
    @JsonProperty("indicators")
    private List<IndicatorDto> indicators;

    @JsonProperty("is_hided")
    private boolean isHided;

    public DocTableDto getObject() {
        return this;
    }
    public String getId() {
        if (this.id == null) {
            return UUID.randomUUID().toString();
        }
        return this.id;
    }


}
