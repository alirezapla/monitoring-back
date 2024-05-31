package com.example.monitor.management.common.validators;


import com.example.monitor.management.domain.model.IndicatorType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IndicatorTypeSubSetValidator implements ConstraintValidator<IndicatorTypeSubset, IndicatorType> {
    private IndicatorType[] subset;

    @Override
    public void initialize(IndicatorTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(IndicatorType indicatorType, ConstraintValidatorContext constraintValidatorContext) {
        return indicatorType == null || Arrays.asList(subset).contains(indicatorType);
    }


}