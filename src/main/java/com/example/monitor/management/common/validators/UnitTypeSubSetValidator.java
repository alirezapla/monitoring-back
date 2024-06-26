package com.example.monitor.management.common.validators;


import com.example.monitor.management.domain.model.IndicatorType;
import com.example.monitor.management.domain.model.UnitType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class UnitTypeSubSetValidator implements ConstraintValidator<UnitTypeSubset, String> {
    private String[] subset;

    @Override
    public void initialize(UnitTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(String unitType, ConstraintValidatorContext constraintValidatorContext) {
        return unitType == null || Arrays.asList(subset).contains(unitType);
    }


}