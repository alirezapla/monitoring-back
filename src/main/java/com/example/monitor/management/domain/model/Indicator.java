package com.example.monitor.management.domain.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "indicator")
@EqualsAndHashCode(exclude = "docTable", callSuper = false)
public class Indicator extends BaseModel<Indicator> {
    @Column(name = "name")
    private String name;

    @Column(name = "record_order")
    @JsonProperty("record_order")
    private Integer order;

    @Column(name = "transaltion_fa")
    @JsonProperty("transaltion_fa")
    private String transaltionFa;

    @Column(name = "transaltion_en")
    @JsonProperty("transaltion_en")
    private String transaltionEn;

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

    @Convert(converter = JsonConvertor.class)
    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @JsonProperty("computation")
    private Set<Computation> computation;


    public Indicator() {
    }

    public Indicator(String id,
                     String name,
                     Integer order,
                     String transaltionFa,
                     String transaltionEn,
                     String descriptionFa,
                     String descriptionEn,
                     DataType dataType,
                     IndicatorType indicatorType,
                     UnitType unitType,
                     Set<Computation> computation,
                     DocTable doctable
    ) {
        super(id);
        this.name = name;
        this.order = order;
        this.transaltionFa = transaltionFa;
        this.transaltionEn = transaltionEn;
        this.descriptionFa = descriptionFa;
        this.descriptionEn = descriptionEn;
        this.dataType = dataType;
        this.indicatorType = indicatorType;
        this.unitType = unitType;
        this.computation = computation;
        this.docTable = doctable;

    }

    public void visible(boolean isHide) {
        this.isHided = isHide;
    }
}
