package com.example.monitor.management.domain.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "indicator")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class Indicator extends BaseModel<Indicator> {
    @Column(name = "name")
    private String name;

    @Column(name = "record_order")
    @JsonProperty("record_order")
    private Integer order;

    @Column(name = "translation_fa")
    @JsonProperty("translation_fa")
    private String translationFa;

    @Column(name = "translation_en")
    @JsonProperty("translation_en")
    private String translationEn;

    @Column(name = "description_fa")
    @JsonProperty("description_fa")
    private String descriptionFa;

    @Column(name = "description_en")
    @JsonProperty("description_en")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    @JsonProperty("data_type")
    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicator_type")
    @JsonProperty("indicator_type")
    private IndicatorType indicatorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    @JsonProperty("unit_type")
    private UnitType unitType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_table_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @JsonIgnoreProperties("docTable")
    private DocTable docTable;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicator", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("indicator")
    @JsonProperty("computations")
    @NotAudited
    @Fetch(FetchMode.SUBSELECT)
    private Set<Computation> computations;

    public Indicator() {
    }

    public Indicator(String id,
                     String name,
                     Integer order,
                     String translationFa,
                     String translationEn,
                     String descriptionFa,
                     String descriptionEn,
                     DataType dataType,
                     IndicatorType indicatorType,
                     UnitType unitType,
                     DocTable doctable
    ) {
        super(id);
        this.name = name;
        this.order = order;
        this.translationFa = translationFa;
        this.translationEn = translationEn;
        this.descriptionFa = descriptionFa;
        this.descriptionEn = descriptionEn;
        this.dataType = dataType;
        this.indicatorType = indicatorType;
        this.unitType = unitType;
        this.computations = new HashSet<>();
        this.docTable = doctable;

    }

    public Indicator insertComputations(Set<Computation> computations) {
        this.computations = computations;
        return this;
    }

    public void addComputation(Computation computation) {
        this.computations.add(computation);
    }

    public void visible(boolean isHide) {
        this.isHided = isHide;
    }

}
