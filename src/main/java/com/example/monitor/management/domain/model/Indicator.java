package com.example.monitor.management.domain.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "doc_table_id", nullable = false)
    private DocTable docTable;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "indicator",fetch=FetchType.LAZY)
    private List<Computation> computation;
}
