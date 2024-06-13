package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;



@Setter
@Getter
@Entity
@Table(name = "computing_table_items")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class ComputingTableItems extends BaseModel<ComputingTableItems> {

    @Column(name = "description")
    private String description;

    @Column(name = "command")
    private String command;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    @JsonBackReference
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
