package com.example.monitor.management.domain.service;

import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.Dto.DocumentNameAndIdDto;
import com.example.monitor.management.common.Dto.DocumentResponseDto;
import com.example.monitor.management.common.MyLogger;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.InvalidClientPerspective;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.ComputingTableItems;
import com.example.monitor.management.domain.model.DocTable;
import com.example.monitor.management.domain.model.Document;
import com.example.monitor.management.domain.model.DocumentRepository;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.example.monitor.management.common.AppLogEvent;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TableService tableService;
    private final ComputingTableService computingTableService;

    public DocumentService(DocumentRepository documentRepository, TableService tableService, ComputingTableService computingTableService, MyLogger logger) {
        this.documentRepository = documentRepository;
        this.computingTableService = computingTableService;
        this.tableService = tableService;
    }

    public Set<DocumentNameAndIdDto> retrieveAll(PageRequest pageRequest, String searchTerm) {
        logEvent(LogLevel.INFO, AppLogEvent.RETRIEVE_ALL_DOCUMENTS_SERVICE_STARTED, "");
        Page<Document> pageableDocuments = documentRepository.findAllDocument(pageRequest, searchTerm);
        Set<DocumentNameAndIdDto> documents = pageableDocuments.stream()
                .map(DocumentNameAndIdDto::new)
                .collect(Collectors.toSet());
        logEvent(LogLevel.INFO, AppLogEvent.RETRIEVE_ALL_DOCUMENTS_SERVICE_FINISHED, "");
        return documents;
    }

    public Document retrieve(String docId, String clientPerspective) {
        logEvent(LogLevel.INFO, AppLogEvent.RETRIEVE_DOCUMENT_SERVICE_STARTED, docId);
        switch (clientPerspective) {
            case "View":
                return getUnhidedDocument(docId);
            case "Edit":
                return getDocument(docId);
            default:
                throw new InvalidClientPerspective("Client perspectives must be `View` or `Edit`");
        }
    }
    
    @Transactional
    public Document create(UserDetails customUserDetails, BodyDto createBodyDto) {
        logEvent(LogLevel.INFO, AppLogEvent.CREATE_DOCUMENT_SERVICE_STARTED, "");

        Document document = new Document(UUID.randomUUID().toString(),
                createBodyDto.getName(),
                createBodyDto.getDescription());
        document.setDocTables(tableService.createTable(customUserDetails, document, createBodyDto));
        document.setComputingTableItems(computingTableService.create(document, createBodyDto));
        document.setCreatedBy(customUserDetails.getUsername());
        documentRepository.save(document);

        logEvent(LogLevel.INFO, AppLogEvent.CREATE_DOCUMENT_SERVICE_FINISHED, document.getId());
        return document;
    }

    @Transactional
    public DocumentResponseDto update(UserDetails customUserDetails, String docId, BodyDto bodyDto) {
        logEvent(LogLevel.INFO, AppLogEvent.UPDATE_DOCUMENT_SERVICE_STARTED, docId);
        Document document = getDocument(docId);

        document.setDescription(bodyDto.getDescription());
        document.setHided(bodyDto.isHided());
        document.setDocTables(tableService.update(customUserDetails, document, bodyDto.getDocTableDto()));
        document.setComputingTableItems(computingTableService.update(document, bodyDto));
        document.setUpdatedBy(customUserDetails.getUsername());
        documentRepository.save(document);

        logEvent(LogLevel.INFO, AppLogEvent.UPDATE_DOCUMENT_SERVICE_FINISHED, document.getId());
        return bodyDto.isHided() ? null : new DocumentResponseDto(document);
    }

    @Transactional
    public String remove(String docId) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.REMOVE_DOCUMENT_SERVICE_STARTED, docId);
        documentRepository.deleteById(docId);
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.REMOVE_DOCUMENT_SERVICE_FINISHED, docId);
        return "done";
    }

    private Document getDocument(String docId) {
        return documentRepository.find(docId)
                .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
    }

    private Document getUnhidedDocument(String docId) {
        return documentRepository.findIsNotHided(docId)
                .orElseThrow(() -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
    }
    
    private void logEvent(LogLevel level, AppLogEvent event, String message) {
        MyLogger.doLog(level, event, message);
    }
}
