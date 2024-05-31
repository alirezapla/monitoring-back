package com.example.monitor.management.api.exception;

import com.example.monitor.management.common.exceptions.BaseApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BaseApplicationException.class)
    protected ResponseEntity<Object> handleBaseApplicationException(Exception ex) {
        ex.printStackTrace();
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

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