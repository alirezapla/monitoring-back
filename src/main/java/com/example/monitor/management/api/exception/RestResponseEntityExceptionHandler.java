package com.example.monitor.management.api.exception;

import com.example.monitor.management.common.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BaseApplicationException.class)
    protected ResponseEntity<Object> handleBaseApplicationException(Exception ex) {
        ex.printStackTrace();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        return ResponseEntity.status(status).body(this.body(ex.getMessage(), status));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class,InvalidClientPerspective.class, OrderDuplicatedException.class, BodyValidationException.class})
    protected ResponseEntity<Object> handleBadRequestException(Exception ex) {
        ex.printStackTrace();
        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status).body(this.body(ex.getMessage(), status));
    }

    @ExceptionHandler(value = {RecordNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(Exception ex) {
        ex.printStackTrace();
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(this.body(ex.getMessage(), status));
    }

    private Map<String, Object> body(String message, Integer status) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status);
        body.put("message", message);
        body.put("timestamp", System.currentTimeMillis());
        return body;
    }
}