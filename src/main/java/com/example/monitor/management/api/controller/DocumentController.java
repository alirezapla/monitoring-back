package com.example.monitor.management.api.controller;


import com.example.monitor.management.api.responseentity.ResponseEntityUtil;
import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.api.utils.httputil.response.SuccessfulRequestResponseEntity;
import com.example.monitor.management.common.AppLogEvent;
import com.example.monitor.management.common.validators.BodyValidator;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.common.exceptions.BodyValidationException;
import com.example.monitor.management.domain.service.DocumentService;
import com.example.monitor.management.infrastructure.security.authentication.JwtAuthentication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "document")
public class DocumentController {
    private final DocumentService documentService;
    private final MyLogger logger;

    public DocumentController(DocumentService documentService, MyLogger logger) {

        this.documentService = documentService;
        this.logger = logger;
    }

    @PostMapping()
    public ResponseEntity<Object> createDocuments(@Valid @RequestBody BodyDto bodyDto,
                                                  BindingResult bindingResult, Authentication authentication) throws BodyValidationException {
        logger.doLog(LogLevel.INFO, AppLogEvent.CREATE_REQUEST_RECEIVED, bodyDto);
        BodyValidator.isValid(bodyDto, bindingResult);
        return responseHandler(documentService.create(((JwtAuthentication) authentication).getUserDetails(), bodyDto), AppLogEvent.CREATE_RESPONSE_SENT);
    }

    @GetMapping(value = "/{docId}")
    public ResponseEntity<Object> getDocuments(
            @RequestParam int page,
            @RequestParam int perPage,
            @PathVariable String docId,
            @RequestParam(required = true) String clientPerspective
    ) {
        logger.doLog(LogLevel.INFO, AppLogEvent.FETCH_REQUEST_RECEIVED, docId);
        return responseHandler(
                documentService.retrieve(pageRequest(page, perPage), docId, clientPerspective),
                AppLogEvent.FETCH_RESPONSE_SENT);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllDocuments(
            @RequestParam int page,
            @RequestParam int perPage,
            @RequestParam(required = false) String searchTerm) {
        logger.doLog(LogLevel.INFO, AppLogEvent.FETCH_REQUEST_RECEIVED, "");
        return responseHandler(
                documentService.retrieveAll(pageRequest(page, perPage), searchTerm),
                AppLogEvent.FETCH_RESPONSE_SENT);
    }

    @PutMapping(value = "/{docId}")
    public ResponseEntity<Object> updateDocument(@PathVariable String docId, @Valid @RequestBody
    BodyDto bodyDto, BindingResult bindingResult, Authentication authentication) throws BodyValidationException {
        logger.doLog(LogLevel.INFO, AppLogEvent.EDIT_REQUEST_RECEIVED, docId);
        BodyValidator.isValid(bodyDto, bindingResult);
        return responseHandler(documentService.update(((JwtAuthentication) authentication).getUserDetails(), docId, bodyDto), AppLogEvent.EDIT_RESPONSE_SENT);
    }

    @DeleteMapping(value = "/{docId}")
    public ResponseEntity<Object> removeDocument(@PathVariable String docId, Authentication authentication) throws IOException {
        logger.doLog(LogLevel.INFO, AppLogEvent.DELETE_REQUEST_RECEIVED, docId);
        return responseHandler(documentService.remove(((JwtAuthentication) authentication).getUserDetails(), docId), AppLogEvent.DELETE_RESPONSE_SENT);
    }

    private ResponseEntity<Object> responseHandler(Object data, AppLogEvent event) {
        return ResponseEntityUtil.generateSuccessfulRequestResponseEntity(
                new SuccessfulRequestResponseEntity<>(data), event
        );
    }

    private PageRequest pageRequest(int page, int perPage) {
        PaginationDTO paginationDTO = new PaginationDTO(page, perPage);
        return PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getPerPage());
    }
}
