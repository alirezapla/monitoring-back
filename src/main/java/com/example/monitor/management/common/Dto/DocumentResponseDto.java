package com.example.monitor.management.common.Dto;

import com.example.monitor.management.domain.model.ComputingTableItems;
import com.example.monitor.management.domain.model.DocTable;
import com.example.monitor.management.domain.model.Document;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class DocumentResponseDto {
    private String id;
    private String description;
    private String name;
    private Set<DocTable> docTableDto;
    private Set<ComputingTableItems> computingTableItems;

    public DocumentResponseDto(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.description = document.getDescription();
        this.docTableDto = getUnHidedDocTables(document.getDocTables());
        this.computingTableItems = document.getComputingTableItems().stream().filter(i -> !i.isHided()).collect(Collectors.toSet());
    }

    private Set<DocTable> getUnHidedDocTables(Set<DocTable> docTables) {
        return docTables.stream().filter(docTable -> !docTable.isHided()).peek(docTable -> {
            docTable.setIndicators(docTable.getIndicators().stream().filter(indicator -> !indicator.isHided()).collect(Collectors.toSet()));
        }).collect(Collectors.toSet());
    }
}
