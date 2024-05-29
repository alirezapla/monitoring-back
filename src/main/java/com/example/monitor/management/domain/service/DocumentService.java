package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PaginationDTO;
import com.example.monitor.management.common.Dto.CreateBodyDto;
import com.example.monitor.management.common.Dto.DocTableDto;
import com.example.monitor.management.common.Dto.UpdateBodyDto;
import com.example.monitor.management.domain.model.DocTable;
import com.example.monitor.management.domain.model.DocTableRepository;
import com.example.monitor.management.domain.model.Document;
import com.example.monitor.management.domain.model.DocumentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocTableRepository docTableRepository;

    public DocumentService(DocumentRepository documentRepository, DocTableRepository docTableRepository) {
        this.documentRepository = documentRepository;
        this.docTableRepository = docTableRepository;
    }


    public Object retrive(PaginationDTO paginationDTO, String docId, String searchTerm, String clientPerspective) {
        PageRequest pageRequest = PageRequest.of(paginationDTO.getPage() - 1, paginationDTO.getPerPage());

        if (Objects.equals(clientPerspective, "View")) {
            documentRepository.findAllIsNotHided(pageRequest, docId);
        } else if (Objects.equals(clientPerspective, "Edit")) {
            documentRepository.findAllById(pageRequest, docId);
        }
        return null;
    }

    public Object create(CreateBodyDto createBodyDto) {
        Document document = new Document(UUID.randomUUID().toString(),
                createBodyDto.getName(),
                createBodyDto.getDescription());
        Set docTables = createBodyDto.getDocTableDto().stream()
                .peek(d -> {
                    DocTable docTable = document.addTable(d.getName());
                    docTable.addIndicator(d.getIndicators());
//                    docTableRepository.saveAll(docTable)
                })
                .collect(Collectors.toSet());
        documentRepository.save(document);
        return document;

    }

    public Object update(UpdateBodyDto updateBodyDto) {
        return null;
    }

    public String remove(String docId) {
        documentRepository.deleteById(docId);
        return "done";
    }
}
