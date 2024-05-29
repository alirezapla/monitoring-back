package com.example.monitor.management.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "document")
public class Document extends BaseModel<Document>{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "document",fetch=FetchType.LAZY)
    private Set<DocTable> docTables;
}
