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
        MyLogger.doLog(LogLevel.INFO,logEvent,"");
        return ResponseEntity.status(HttpStatus.OK).body(successfulRequestResponseEntity);
    }

}
