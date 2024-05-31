package com.example.monitor.management.api.responseentity;


import com.example.monitor.management.api.utils.httputil.response.BadRequestResponseEntity;
import com.example.monitor.management.api.utils.httputil.response.ServiceUnavailableResponseEntity;
import com.example.monitor.management.api.utils.httputil.response.SuccessfulRequestResponseEntity;
import com.example.monitor.management.common.AppLogEvent;
import com.example.monitor.management.common.MyLogger;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil {

    private ResponseEntityUtil() {
    }

    public static ResponseEntity<Object> generateSuccessfulRequestResponseEntity(
            SuccessfulRequestResponseEntity<?> successfulRequestResponseEntity, AppLogEvent logEvent
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(successfulRequestResponseEntity);
    }

    public static ResponseEntity<Object> generateServiceUnAvailableResponseEntity(
            ServiceUnavailableResponseEntity<?> serviceUnavailableResponseEntity
    ) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceUnavailableResponseEntity);
    }

    public static ResponseEntity<Object> generateBadRequestResponseEntity(
            BadRequestResponseEntity<?> badRequestResponseEntity
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponseEntity);
    }
}
