package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PageDTO;
import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.Dto.DocumentNameAndIdDto;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.InvalidClientPerspective;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.ComputingTableItems;
import com.example.monitor.management.domain.model.DocTable;
import com.example.monitor.management.domain.model.Document;
import com.example.monitor.management.domain.model.DocumentRepository;
import com.example.monitor.management.domain.model.security.CustomUserDetails;
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

    public Object retrieveAll(PageRequest pageRequest, String searchTerm) {
        Set<DocumentNameAndIdDto> documents=new HashSet<>();
        Page<Document> pageableDocuments;
        if (!(searchTerm==null)){
            pageableDocuments = documentRepository.findByName(pageRequest,searchTerm);
        }else {
            pageableDocuments=documentRepository.findAllByOrderByCreatedDateDesc(pageRequest);
        }
        pageableDocuments.forEach(document -> {
            documents.add(new DocumentNameAndIdDto(document));
        });
        return documents;
    }

    public Object retrieve(PageRequest pageRequest, String docId, String clientPerspective) {
        if (Objects.equals(clientPerspective, "View")) {
            return getNestedData(documentRepository.findIsNotHided(docId),clientPerspective,pageRequest);
        } else if (Objects.equals(clientPerspective, "Edit")) {
            return getNestedData(getDocument(docId),clientPerspective,pageRequest);
        }
        throw new InvalidClientPerspective("client perspectives are `View` or `Edit`");
    }
    private Document getNestedData(Document document,String clientPerspective,PageRequest pageRequest){
        String docId = document.getId();
        if (Objects.equals(clientPerspective, "View")) {
            document.setComputingTableItems(new HashSet<>(computingTableService.retrieveUnHided(docId, pageRequest).getItems()));
            document.setDocTables(new HashSet<>(tableService.retrieveUnHided(docId, pageRequest).getItems()));
            return document;
        }
        document.setComputingTableItems(new HashSet<>(computingTableService.retrieveAll(docId, pageRequest).getItems()));
        document.setDocTables(new HashSet<>(tableService.retrieveAll(docId, pageRequest).getItems()));
        return document;

    }

    @Transactional
    public Document create(CustomUserDetails customUserDetails, BodyDto createBodyDto) {
        Document document = new Document(UUID.randomUUID().toString(),
                createBodyDto.getName(),
                createBodyDto.getDescription());
        documentRepository.save(document);
        Set<ComputingTableItems> computingTableItems = computingTableService.create(document, createBodyDto);
        Set<DocTable> docTables = tableService.createTable(customUserDetails, document, createBodyDto);
        document.setDocTables(docTables);
        document.setComputingTableItems(computingTableItems);
        document.setCreatedBy(customUserDetails.getUserId());
        return document;

    }

    public Object update(CustomUserDetails customUserDetails, String docId, BodyDto bodyDto) {
        Document document = getDocument(docId);
        document.setDescription(bodyDto.getDescription());
        document.setUpdatedBy(customUserDetails.getUserId());
        documentRepository.save(document);
        Set<DocTable> docTable = tableService.update(customUserDetails, document, bodyDto.getDocTableDto());
        document.setDocTables(docTable);
        Set<ComputingTableItems> computingTable = computingTableService.update(document, bodyDto);
        document.setComputingTableItems(computingTable);
        return document;
    }


    private Document getDocument(String docId){
         return documentRepository.findById(docId).orElseThrow(
                () -> new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle()));
    }
    @Transactional
    public String remove(CustomUserDetails customUserDetails, String docId) {
        documentRepository.softDeleteById(customUserDetails.getUserId(), docId);
        return "done";
    }
}
