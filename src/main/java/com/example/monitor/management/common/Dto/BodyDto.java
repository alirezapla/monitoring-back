package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BodyDto {
    @NotNull(message = "The name is required.")
    private String name;
    private String description;

    @JsonProperty("doc_tables")
    @Valid
    private List<DocTableDto> docTableDto;

    @JsonProperty("computing_table")
    @Valid
    private List<ComputingTableItemsDto> computingTableItems;

    @JsonProperty("is_hided")
    private boolean isHided;
    @JsonProperty("is_deleted")
    private boolean isDeleted;

}
