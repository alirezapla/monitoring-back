package com.example.monitor.management.domain.model;

import com.example.monitor.management.common.Dto.ComputingTableItemsDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "computing_table")
public class ComputingTable extends BaseModel<ComputingTable> {


    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "computingTable", fetch = FetchType.LAZY)
    private Set<ComputingTableItems> computingTableItems;

    public ComputingTable() {
    }

    public void addComputingTableItems(ComputingTableItemsDto computingTableItemsDto) {
        this.computingTableItems.add(
                new ComputingTableItems(computingTableItemsDto.getDescription(), computingTableItemsDto.getCommand()));
    }

}
