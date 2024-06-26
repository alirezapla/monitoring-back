package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Audited
@EntityListeners(AuditingEntityListener.class)
@Table(name = "doc_table")
public class DocTable extends BaseModel<DocTable> {
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    @JsonIgnoreProperties("docTables")
    private Document document;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_table_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnoreProperties("docTable")
    private Set<Indicator> indicators;


    public DocTable() {

    }

    public DocTable(String id, String name, Document document) {
        super(id);
        this.name = name;
        this.document = document;
        this.indicators = new HashSet<>();
    }

    public void addIndicator(Indicator indicator) {
        this.indicators.add(indicator);
    }

    public void visible(boolean isHide) {
        this.isHided = isHide;
    }
}
