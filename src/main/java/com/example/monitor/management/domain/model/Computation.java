package com.example.monitor.management.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "computation")
@Audited
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(exclude = "indicator")
public class Computation {
    @Id
    @Size(min = 36, max = 36)
    protected String id;
    @Version
    @JsonIgnore
    private Integer version;
    @Column(name = "label")
    private String label;
    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indicator_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnoreProperties("indicator")
    private Indicator indicator;

    public Computation() {
    }

    public Computation(String label, String description, Indicator indicator) {
        this.id = UUID.randomUUID().toString();
        this.label = label;
        this.description = description;
        this.indicator = indicator;
    }
}
