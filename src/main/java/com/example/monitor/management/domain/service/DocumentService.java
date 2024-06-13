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

    public Object retrieveAll(PageRequest pageRequest, String searchTerm) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.RETRIEVE_ALL_DOCUMENTS_SERVICE_STARTED, "");
        Set<DocumentNameAndIdDto> documents = new HashSet<>();
        Page<Document> pageableDocuments;
        pageableDocuments = documentRepository.findAllDocument(pageRequest, searchTerm);
        pageableDocuments.forEach(document -> {
            documents.add(new DocumentNameAndIdDto(document));
        });
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.RETRIEVE_ALL_DOCUMENTS_SERVICE_FINISHED, "");
        return documents;
    }

    public Document retrieve(String docId, String clientPerspective) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.RETRIEVE_DOCUMENT_SERVICE_STARTED, docId);
        if (Objects.equals(clientPerspective, "View")) {
            return getUnhidedDocument(docId);
        } else if (Objects.equals(clientPerspective, "Edit")) {
            return getDocument(docId);
        }
        throw new InvalidClientPerspective("client perspectives are `View` or `Edit`");
    }


    @Transactional
    public Document create(UserDetails customUserDetails, BodyDto createBodyDto) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.CREATE_DOCUMENT_SERVICE_STARTED, "");
        Document document = new Document(UUID.randomUUID().toString(),
                createBodyDto.getName(),
                createBodyDto.getDescription());
        Set<ComputingTableItems> computingTableItems = computingTableService.create(document, createBodyDto);
        Set<DocTable> docTables = tableService.createTable(customUserDetails, document, createBodyDto);
        document.setDocTables(docTables);
        document.setComputingTableItems(computingTableItems);
        document.setCreatedBy(customUserDetails.getUsername());
        documentRepository.save(document);
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.CREATE_DOCUMENT_SERVICE_FINISHED, document.getId());
        return document;

    }

    public Object update(UserDetails customUserDetails, String docId, BodyDto bodyDto) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.UPDATE_DOCUMENT_SERVICE_STARTED, docId);
        Document document = getDocument(docId);
        document.setDescription(bodyDto.getDescription());
        document.setHided(bodyDto.isHided());
        Set<DocTable> docTable = tableService.update(customUserDetails, document, bodyDto.getDocTableDto());
        document.setDocTables(docTable);
        Set<ComputingTableItems> computingTable = computingTableService.update(document, bodyDto);
        document.setComputingTableItems(computingTable);
        document.setUpdatedBy(customUserDetails.getUsername());
        documentRepository.save(document);
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.UPDATE_DOCUMENT_SERVICE_FINISHED, document.getId());
        if (bodyDto.isHided()) {
            return null;
        }
        return new DocumentResponseDto(document);
    }


    private Document getDocument(String docId) {
        return documentRepository.find(docId).orElseThrow(
                () -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
    }

    private Document getUnhidedDocument(String docId) {
        return documentRepository.findIsNotHided(docId).orElseThrow(
                () -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
    }

    @Transactional
    public String remove(String docId) {
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.REMOVE_DOCUMENT_SERVICE_STARTED, docId);
        documentRepository.deleteById(docId);
        MyLogger.doLog(LogLevel.INFO, AppLogEvent.REMOVE_DOCUMENT_SERVICE_FINISHED, docId);
        return "done";
    }
}
