package com.example.monitor.management.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import com.example.monitor.management.common.Dto.ComputationDto;

@Setter
@Getter
@Entity
@Table(name = "indicator")
public class Indicator extends BaseModel<Indicator> {
    @Column(name = "name")
    private String name;

    @Column(name = "record_order")
    private Integer order;

    @Column(name = "transaltion_fa")
    private String transaltionFa;

    @Column(name = "transaltion_en")
    private String transaltionEn;

    @Column(name = "description_fa")
    private String descriptionFa;

    @Column(name = "description_en")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicator_type")
    private IndicatorType indicatorType;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private UnitType unitType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_table_id", nullable = false)
    private DocTable docTable;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicator", fetch = FetchType.LAZY)
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
                     UnitType unitType
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

    }

    public void addComputation(ComputationDto computationDto) {
        this.computation.add(new Computation(computationDto.getLabel(), computationDto.getDescription()));
    }
}
