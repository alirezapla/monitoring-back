package com.example.monitor.management.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "doc_table")
public class DocTable extends BaseModel<DocTable> {
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docTable", fetch = FetchType.LAZY)
    private Set<Indicator> indicators;
}
