package com.example.monitor.management.api.controller;


import com.example.monitor.management.api.responseentity.ResponseEntityUtil;
import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.api.utils.httputil.response.SuccessfulRequestResponseEntity;
import com.example.monitor.management.common.AppLogEvent;
import com.example.monitor.management.common.validators.BodyValidator;
import com.example.monitor.management.common.validators.ValuesAllowed;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.common.exceptions.BodyValidationException;
import com.example.monitor.management.domain.service.DocumentService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Validated
@RestController
@RequestMapping(value = "documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {

        this.documentService = documentService;
    }

    @PostMapping()
    public ResponseEntity<Object> createDocuments(@Valid @RequestBody BodyDto bodyDto,
                                                  BindingResult bindingResult, Authentication authentication) throws BodyValidationException {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.CREATE_REQUEST_RECEIVED, bodyDto);
        BodyValidator.isValid(bodyDto, bindingResult);
        return responseHandler(documentService.create((UserDetails) authentication.getPrincipal(), bodyDto), AppLogEvent.CREATE_RESPONSE_SENT);
    }

    @GetMapping(value = "/{docId}")
    public ResponseEntity<Object> getDocuments(
            @PathVariable String docId,
            @RequestParam(required = true) String clientPerspective
    ) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.FETCH_REQUEST_RECEIVED, docId);
        return responseHandler(
                documentService.retrieve(docId, clientPerspective),
                AppLogEvent.FETCH_RESPONSE_SENT);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllDocuments(
            @RequestParam(required = true) int page,
            @RequestParam(required = true) int perPage,
            @RequestParam(required = false) @ValuesAllowed(values = {"name", "id"}) String orderBy,
            @RequestParam(required = false) String searchTerm) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.FETCH_REQUEST_RECEIVED, "");
        return responseHandler(
                documentService.retrieveAll(pageRequest(page, perPage, orderBy), searchTerm),
                AppLogEvent.FETCH_RESPONSE_SENT);
    }

    @PutMapping(value = "/{docId}")
    public ResponseEntity<Object> updateDocument(@PathVariable String docId, @Valid @RequestBody
    BodyDto bodyDto, BindingResult bindingResult, Authentication authentication) throws BodyValidationException {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.EDIT_REQUEST_RECEIVED, docId);
        BodyValidator.isValid(bodyDto, bindingResult);
        return responseHandler(documentService.update((UserDetails) authentication.getPrincipal(), docId, bodyDto), AppLogEvent.EDIT_RESPONSE_SENT);
    }

    @DeleteMapping(value = "/{docId}")
    public ResponseEntity<Object> removeDocument(@PathVariable String docId, Authentication authentication) throws IOException {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.DELETE_REQUEST_RECEIVED, docId);
        return responseHandler(documentService.remove((docId)), AppLogEvent.DELETE_RESPONSE_SENT);
    }

    private ResponseEntity<Object> responseHandler(Object data, AppLogEvent event) {
        return ResponseEntityUtil.generateSuccessfulRequestResponseEntity(
                new SuccessfulRequestResponseEntity<>(data), event
        );
    }

    private PageRequest pageRequest(int page, int perPage, String orderBy) {
        PaginationDTO paginationDTO = new PaginationDTO(page, perPage);
        if (!orderBy.isEmpty()) {
            return PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getPerPage(), Sort.by(orderBy));
        }
        return PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getPerPage());
    }
}
