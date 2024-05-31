package com.example.monitor.management.common.Dto;

import java.util.List;

public interface DocTableInterface {

    public String getName();

    public boolean getIsHided();

    public List<IndicatorDto> getIndicators();
}
