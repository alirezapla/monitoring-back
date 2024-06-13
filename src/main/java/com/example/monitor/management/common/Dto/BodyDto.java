package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BodyDto {
    @NotNull(message = "The name is required.")
    private String name;
    private String description;

    @JsonProperty("document_tables")
    @Valid
    private Set<DocTableDto> docTableDto;

    @JsonProperty("computing_table")
    @Valid
    private Set<ComputingTableItemsDto> computingTableItems;

    @JsonProperty("is_hided")
    private boolean isHided;


}
