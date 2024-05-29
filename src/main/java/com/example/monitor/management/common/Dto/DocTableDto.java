package com.example.monitor.management.common.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;


@Getter
@Setter
public class DocTableDto {
    private String name;

    @Valid
    private List<IndicatorDto> indicators;

}
