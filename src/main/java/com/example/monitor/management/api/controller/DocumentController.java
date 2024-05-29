package com.example.monitor.management.api.controller;


import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.api.utils.httputil.response.SuccessfulRequestResponseEntity;
import com.example.monitor.management.common.Dto.CreateBodyDto;
import com.example.monitor.management.common.Dto.UpdateBodyDto;
import com.example.monitor.management.domain.service.DocumentService;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.api.responseentity.ResponseEntityUtil;
import com.example.monitor.management.common.AppLogEvent;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(value = "document")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {

        this.documentService = documentService;
    }

    @PostMapping()
    public ResponseEntity<Object> createDocuments(@Valid @RequestBody CreateBodyDto createBodyDto) throws Exception {
//        MyLogger.doLog(LogLevel.INFO, AppLogEvent.CREATE_REQUEST_RECEIVED, createBodyDto);
        return responseHandler(documentService.create(createBodyDto), AppLogEvent.CREATE_RESPONSE_SENT);
    }

    @GetMapping(value = "/{docId}")
    public ResponseEntity<Object> getDocuments(
            @RequestParam int page,
            @RequestParam int perPage,
            @PathVariable String docId,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = true) String clientPerspective
    ) throws Exception {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.FETCH_REQUEST_RECEIVED, docId);
        return responseHandler(documentService.retrive(new PaginationDTO(page, perPage),docId, searchTerm, clientPerspective),
                AppLogEvent.FETCH_RESPONSE_SENT);

    }

    @PutMapping(value = "/{docId}")
    public ResponseEntity<Object> updateDocument(@PathVariable String docId, @Valid @RequestBody
    UpdateBodyDto updateBodyDto) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.EDIT_REQUEST_RECEIVED, docId);
        return responseHandler(documentService.update(updateBodyDto), AppLogEvent.EDIT_RESPONSE_SENT);
    }

    @DeleteMapping(value = "/{docId}")
    public ResponseEntity<Object> removeDocument(@PathVariable String docId) throws IOException {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.DELETE_REQUEST_RECEIVED, docId);
        return responseHandler(documentService.remove(docId), AppLogEvent.DELETE_RESPONSE_SENT);
    }

    private ResponseEntity<Object> responseHandler(Object data, AppLogEvent event) {
        return ResponseEntityUtil.generateSuccessfulRequestResponseEntity(
                new SuccessfulRequestResponseEntity<>(data), event
        );
    }
}
