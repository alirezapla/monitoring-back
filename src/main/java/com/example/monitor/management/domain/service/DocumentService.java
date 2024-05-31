package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PageDTO;
import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.ComputingTableItems;
import com.example.monitor.management.domain.model.DocTable;
import com.example.monitor.management.domain.model.Document;
import com.example.monitor.management.domain.model.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final TableService tableService;
    private final ComputingTableService computingTableService;

    public DocumentService(DocumentRepository documentRepository, TableService tableService, ComputingTableService computingTableService) {
        this.documentRepository = documentRepository;
        this.computingTableService = computingTableService;
        this.tableService = tableService;
    }


    public Object retrieve(PaginationDTO paginationDTO, String docId, String searchTerm, String clientPerspective) {
        PageRequest pageRequest = PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getPerPage());

        if (Objects.equals(clientPerspective, "View")) {
            Page<Document> documents = documentRepository.findAllIsNotHided(pageRequest, docId);
            documents.forEach(d -> {
                d.setComputingTableItems(new HashSet<>(computingTableService.retrieveAll(docId, pageRequest).getItems()));
                d.setDocTables(new HashSet<>(tableService.retrieveAll(docId, pageRequest).getItems()));
            });
            return new PageDTO<>(
                    documents.getContent(), paginationDTO.getPage(), paginationDTO.getPerPage(), documents.getTotalElements());
        } else if (Objects.equals(clientPerspective, "Edit")) {
            Page<Document> documents = documentRepository.findAllById(pageRequest, docId);
            documents.forEach(d -> {
                d.setComputingTableItems(new HashSet<>(computingTableService.retrieveUnHided(docId, pageRequest).getItems()));
                d.setDocTables(new HashSet<>(tableService.retrieveUnHided(docId, pageRequest).getItems()));
            });
            return new PageDTO<>(
                    documents.getContent(), paginationDTO.getPage(), paginationDTO.getPerPage(), documents.getTotalElements());

        }
        return null;
    }

    @Transactional
    public Document create(BodyDto createBodyDto) {
        Document document = new Document(UUID.randomUUID().toString(),
                createBodyDto.getName(),
                createBodyDto.getDescription());
        documentRepository.save(document);
        computingTableService.create(document, createBodyDto);
        tableService.createTable(document, createBodyDto);
        return document;

    }

    public Object update(String docId, BodyDto updateBodyDto) {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
        return updateDocumentFields(document, updateBodyDto);
    }

    private Document updateDocumentFields(Document document, BodyDto bodyDto) {
        document.setDescription(bodyDto.getDescription());
        documentRepository.save(document);
        Set<DocTable> docTable = tableService.update(document, bodyDto.getDocTableDto());
        document.setDocTables(docTable);
        Set<ComputingTableItems> computingTable = computingTableService.update(document, bodyDto);
        document.setComputingTableItems(computingTable);
        return document;

    }


    public String remove(String docId) {
        documentRepository.deleteById(docId);
        return "done";
    }
}
