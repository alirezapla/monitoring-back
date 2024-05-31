package com.example.monitor.management.common.Dto;

import com.example.monitor.management.domain.model.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentNameAndIdDto {
    private String id;
    private String name;

    public DocumentNameAndIdDto(Document document) {
        this.id = document.getId();
        this.name = document.getName();

    }
}
