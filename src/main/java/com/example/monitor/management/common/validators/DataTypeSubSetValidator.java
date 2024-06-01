package com.example.monitor.management.common.validators;


import com.example.monitor.management.common.exceptions.BodyValidationException;
import com.example.monitor.management.domain.model.DataType;
import com.example.monitor.management.domain.model.IndicatorType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class DataTypeSubSetValidator implements ConstraintValidator<DataTypeSubset, String> {
    private String[] subset;

    @Override
    public void initialize(DataTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(String dataType, ConstraintValidatorContext constraintValidatorContext) {
        if (Arrays.asList(subset).contains(dataType)){
            return true;
        }
        return false;
    }


}