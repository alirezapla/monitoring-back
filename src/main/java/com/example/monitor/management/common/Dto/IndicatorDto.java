package com.example.monitor.management.common.Dto;

import com.example.monitor.management.common.validators.DataTypeSubset;
import com.example.monitor.management.common.validators.IndicatorTypeSubset;
import com.example.monitor.management.common.validators.UnitTypeSubset;
import com.example.monitor.management.domain.model.Computation;
import com.example.monitor.management.domain.model.DataType;
import com.example.monitor.management.domain.model.IndicatorType;
import com.example.monitor.management.domain.model.UnitType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;


@Getter
@Setter
public class IndicatorDto {

    private String id;

    @NotNull(message = "The name is required.")
    private String name;
    @NotNull(message = "The order is required.")
    @Min(value = 1, message = "The order must be greater than 0")
    @JsonProperty("record_order")
    private Integer order;
    @NotNull(message = "The transaltion_fa is required.")
    @JsonProperty("transaltion_fa")
    private String transaltionFa;
    @NotNull(message = "The transaltion_en is required.")
    @JsonProperty("transaltion_en")
    private String transaltionEn;
    @NotNull(message = "The description_fa is required.")
    @JsonProperty("description_fa")
    private String descriptionFa;
    @NotNull(message = "The description_en is required.")
    @JsonProperty("description_en")
    private String descriptionEn;
    @NotNull
    @DataTypeSubset(anyOf = {DataType.DATETIME, DataType.FLOAT, DataType.ENUM, DataType.STRING, DataType.INT})
    @JsonProperty("data_type")
    private DataType dataType;
    @NotNull
    @IndicatorTypeSubset(anyOf = {IndicatorType.KPI, IndicatorType.DYNAMIC_DATA})
    @JsonProperty("indicator_type")
    private IndicatorType indicatorType;
    @NotNull
    @UnitTypeSubset(anyOf = {UnitType.MS, UnitType.S, UnitType.H, UnitType.M})
    @JsonProperty("unit_type")
    private UnitType unitType;

    @JsonProperty("is_hided")
    private boolean isHided;
    @JsonProperty("is_deleted")
    private boolean isDeleted;


    //    @Valid
    @Size(min = 1, message = "Input computation set cannot be empty.")
    @NotNull(message = "computations must not be null")
    @JsonProperty("computations")
    private Set<Computation> computation;


    public IndicatorDto getObject() {
        return this;
    }

}
