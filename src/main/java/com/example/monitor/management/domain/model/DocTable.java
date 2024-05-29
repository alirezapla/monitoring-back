package com.example.monitor.management.domain.model;

import com.example.monitor.management.common.Dto.IndicatorDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "doc_table")
public class DocTable extends BaseModel<DocTable> {
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docTable", fetch = FetchType.LAZY)
    private Set<Indicator> indicators;

    public DocTable() {

    }

    public DocTable(String id, String name) {
        super(id);
        this.name = name;
    }

    public void addIndicator(List<IndicatorDto> indicatorDtos) {
        indicatorDtos.stream().forEach(t ->
                this.indicators.add(
                        new Indicator(
                                UUID.randomUUID().toString(),
                                t.getName(),
                                t.getOrder(),
                                t.getTransaltionFa(),
                                t.getTransaltionEn(),
                                t.getDescriptionFa(),
                                t.getDescriptionEn(),
                                DataType.valueOf(t.getDataType()),
                                IndicatorType.valueOf(t.getIndicatorType()),
                                UnitType.valueOf(t.getUnitType())
                        )
                )
        );
    }
}
