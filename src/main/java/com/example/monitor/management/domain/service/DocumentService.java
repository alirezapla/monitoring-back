package com.example.monitor.management.domain.service;

import com.example.monitor.management.common.Dto.CreateBodyDto;
import com.example.monitor.management.common.Dto.UpdateBodyDto;
import com.example.monitor.management.domain.model.DocTableRepository;
import com.example.monitor.management.domain.model.DocumentRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public Object retrive(String docId, String searchTerm, String clientPerspective) {
        return null;
    }

    public Object create(CreateBodyDto createBodyDto) {
        return null;
    }

    public Object update(UpdateBodyDto updateBodyDto) {
        return null;
    }

    public Object remove(String docId) {
        return null;
    }
}
