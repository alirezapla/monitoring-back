package com.example.monitor.management.api.controller;


import com.example.monitor.management.api.responseentity.ResponseEntityUtil;
import com.example.monitor.management.api.utils.httputil.response.SuccessfulRequestResponseEntity;
import com.example.monitor.management.domain.service.user.AuthenticationService;
import com.example.monitor.management.domain.service.user.dto.LoginDTO;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.common.AppLogEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final HttpServletRequest request;
    private final MyLogger logger;


    public AuthenticationController(AuthenticationService authenticationService, HttpServletRequest request,
                                    MyLogger logger) {
        this.authenticationService = authenticationService;
        this.request = request;
        this.logger = logger;
    }

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginDTO) {
        logger.doLog(LogLevel.INFO, AppLogEvent.LOGIN_REQUEST_RECEIVED, loginDTO);
        return ResponseEntityUtil.generateSuccessfulRequestResponseEntity(
                new SuccessfulRequestResponseEntity<>(authenticationService.login(loginDTO)),
                AppLogEvent.LOGIN_RESPONSE_SENT
        );
    }

}

