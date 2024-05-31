package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.UUID;


@Setter
@Getter
@Entity
@Table(name = "computing_table_items")
@EqualsAndHashCode(exclude = "document", callSuper = false)
public class ComputingTableItems extends BaseModel<DocTable> {

    @Column(name = "description")
    private String description;

    @Column(name = "command")
    private String command;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnoreProperties("computingTableItems")
    private Document document;


    public ComputingTableItems() {

    }

    public ComputingTableItems(String id, String description, String command, Document document) {
        super(id);
        this.description = description;
        this.command = command;
        this.document = document;
    }

    public void visible(boolean isHide) {
        this.isHided = isHide;
    }

}
