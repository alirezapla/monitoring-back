package com.example.monitor.management.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "computation")
public class Computation {
    @Id
    @Size(min = 36, max = 36)
    protected String id;
    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id", nullable = false)
    private Indicator indicator;

    public Computation() {

    }
    public Computation(String label, String description) {
        this.id = UUID.randomUUID().toString();
        this.label = label;
        this.description = description;
    }

}
