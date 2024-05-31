package com.example.monitor.management.common.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
public class BodyDto {
    @NotNull(message = "The name is required.")
    private String name;
    private String description;

    @JsonProperty("doc_tables")
    private List<DocTableDto> docTableDto;

    @JsonProperty("computing_table")
    private List<ComputingTableItemsDto> computingTableItems;

}
