package com.example.monitor.management.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyLogger {
    private static Logger logger = LoggerFactory.getLogger(MyLogger.class);

    public MyLogger() {

    }

    public static void doLog(LogLevel logLevel,AppLogEvent event, Object payload) {
        String message = new HashMap<String, Object>() {{
            put("Event", event);
            put("Body", payload);
            put("timestamp", ZonedDateTime.now().toInstant().toEpochMilli());
        }}.toString();
        switch (logLevel) {
            case DEBUG:
                logger.debug(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case INFO:
                logger.info(message);
            case OFF:
        }

    }

}