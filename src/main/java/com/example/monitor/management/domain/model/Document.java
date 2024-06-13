package com.example.monitor.management.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;


@Setter
@Getter
@Entity
@Table(name = "document")
@Audited
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = {"docTables", "computingTable"}, callSuper = false)
public class Document extends BaseModel<Document> {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document", fetch = FetchType.LAZY)
    @JsonProperty("document_tables")
    @NotAudited
    private Set<DocTable> docTables;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document", fetch = FetchType.LAZY)
    @JsonProperty("computing_table")
    @NotAudited
    private Set<ComputingTableItems> computingTableItems;


    public Document() {

    }

    public Document(String id, String name, String description) {
        super(id);
        this.name = name;
        this.description = description;
        this.docTables = new HashSet<>();
        this.computingTableItems = new HashSet<>();
    }




    public void addComputingItem(ComputingTableItems computingTable) {
        this.computingTableItems.add(computingTable);
    }


}
