package com.example.monitor.management.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "document")
public class Document extends BaseModel<Document> {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document", fetch = FetchType.LAZY)
    private Set<DocTable> docTables;


    @OneToOne(mappedBy = "document")
    private ComputingTable computingTable;

    public Document() {

    }

    public Document(String id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
        this.computingTable = new ComputingTable();
    }

    public DocTable addTable(String docTableName) {
        DocTable doctable = new DocTable(UUID.randomUUID().toString(), docTableName);
        this.docTables.add(doctable);
        return doctable;
    }
}
