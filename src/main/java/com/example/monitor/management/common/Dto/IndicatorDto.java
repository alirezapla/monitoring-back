package com.example.monitor.management.common.Dto;

import com.example.monitor.management.domain.model.DataType;
import com.example.monitor.management.domain.model.IndicatorType;
import com.example.monitor.management.domain.model.UnitType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import java.util.List;


@Getter
@Setter
public class IndicatorDto {
//    @NotNull(message = "The name is required.")
    private String name;
//    @NotNull(message = "The order is required.")
//    @Min(value = 1, message = "The order must be greater than 0")
    private Integer order;
//    @NotNull(message = "The transaltionFa is required.")
    private String transaltionFa;
//    @NotNull(message = "The transaltionEn is required.")
    private String transaltionEn;
//    @NotNull(message = "The descriptionFa is required.")
    private String descriptionFa;
//    @NotNull
    private String descriptionEn;
//    @NotNull
    private String dataType;
//    @NotNull
    private String indicatorType;
//    @NotNull
    private String unitType;

    @NotEmpty(message = "Input computation list cannot be empty.")
    private List<String> computation;
}
