package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;


@Setter
@Getter
@Entity
@Table(name = "computing_table_items")
@EqualsAndHashCode(exclude = "computingTable")
public class ComputingTableItems {

    @Id
    @Size(min = 36, max = 36)
    protected String id;

    @Column(name = "description")
    private String description;

    @Column(name = "command")
    private String command;

    @Column(name = "is_hided")
    private boolean isHided;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnoreProperties("computingTableItems")
    private Document document;


    public ComputingTableItems() {

    }
    public ComputingTableItems(String description, String command,Document document) {
        this.description = description;
        this.command = command;
        this.document = document;
    }
    public void visible(boolean isHide) {
        this.isHided = isHide;
    }

}
