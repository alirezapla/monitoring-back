package com.example.monitor.management.common;

import com.example.monitor.management.api.utils.httputil.response.BadRequestResponseEntity;
import com.example.monitor.management.common.exceptions.BodyValidationException;
import org.springframework.validation.BindingResult;

public class BodyValidator {

    public static void isValid(Object body, BindingResult bindingResult) throws BodyValidationException {

        if (bindingResult.hasErrors()) {
            String messageKey = bindingResult.getFieldErrors().get(0).getDefaultMessage();
            throw new BodyValidationException(messageKey);
        }
    }

}
