package com.example.monitor.management.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Setter
@Getter
@Entity
@Table(name = "computing_table_items")
public class ComputingTableItems {

    @Id
    @Size(min = 36, max = 36)
    protected String id;

    @Column(name = "description")
    private String description;

    @Column(name = "command")
    private String command;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computing_table_id", nullable = false)
    private ComputingTable computingTable;


    public ComputingTableItems() {

    }
    public ComputingTableItems(String description, String command) {
        this.description = description;
        this.command = command;
    }

}
