package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PageDTO;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.Dto.DocTableDto;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.*;
import com.example.monitor.management.domain.model.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TableService {
    private final DocTableRepository docTableRepository;
    private final IndicatorService indicatorService;

    public TableService(DocTableRepository docTableRepository, IndicatorService indicatorService) {
        this.docTableRepository = docTableRepository;
        this.indicatorService = indicatorService;
    }

    public PageDTO<DocTable> retrieveAll(String docId, PageRequest pageRequest) {
        Page<DocTable> docTables = docTableRepository.findAllByDocumentId(pageRequest, docId);
        docTables.forEach(d -> {
            d.setIndicators(new HashSet<>(indicatorService.retrieveAll(d.getId(), pageRequest).getItems()));
        });
        return new PageDTO<>(docTables.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), docTables.getTotalElements());

    }

    public PageDTO<DocTable> retrieveUnHided(String docId, PageRequest pageRequest) {
        Page<DocTable> docTables = docTableRepository.findAllUnHidedByDocumentId(pageRequest, docId);
        docTables.forEach(d -> {
            d.setIndicators(new HashSet<>(indicatorService.retrieveUnHided(d.getId(), pageRequest).getItems()));
        });
        return new PageDTO<>(docTables.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), docTables.getTotalElements());

    }

    @Transactional
    public Set<DocTable> createTable(CustomUserDetails customUserDetails, Document document, BodyDto createBodyDto) {
        Set<DocTable> docTables = new HashSet<>();
        createBodyDto.getDocTableDto().forEach(d -> {
            docTables.add(createNewDocTale(customUserDetails, document, d));
        });
        return docTables;
    }

    public Set<DocTable> update(CustomUserDetails customUserDetails, Document document, List<DocTableDto> updateTableDto) {
        List<DocTable> docTables = getDocTables(document);
        Map<String, DocTableDto> tableMap = convertDocTableToMap(updateTableDto);
        Set<DocTable> updatedDocTables = new HashSet<>();
        docTables.forEach(d -> {
            if (tableMap.containsKey(d.getId())) {
                DocTableDto tableDto = tableMap.get(d.getId());
                d.setName(tableDto.getName());
                d.visible(tableDto.isHided());
                d.setUpdatedBy(customUserDetails.getUserId());
                updatedDocTables.add(d);
                updateTableDto.remove(tableDto);
                docTableRepository.save(d);
                indicatorService.updateIndicators(customUserDetails, d, tableDto.getIndicators());
            }
        });
        omitDeletedTables(updatedDocTables, docTables);
        if (updateTableDto.size() > 0) {
            updateTableDto.forEach(i -> {
                updatedDocTables.add(createNewDocTale(customUserDetails, document, i));
            });
        }
        return updatedDocTables;
    }

    private List<DocTable> getDocTables(Document document) {
        List<DocTable> docTables = docTableRepository.findAllByDocumentId(document.getId());
        if (docTables.isEmpty()) {
            throw new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle());
        }
        return docTables;
    }

    private void omitDeletedTables(Set<DocTable> updatedDocTables, List<DocTable> currentDocTables) {
        Set<String> updatedDocTablesIds = updatedDocTables.stream().map(BaseModel::getId).collect(Collectors.toSet());
        currentDocTables.parallelStream().filter(i -> !updatedDocTablesIds.contains((i.getId()))).forEach(docTableRepository::delete);
    }

    private Map<String, DocTableDto> convertDocTableToMap(List<DocTableDto> updateTableDto) {
        return updateTableDto.stream().collect(Collectors.toMap(DocTableDto::getId, DocTableDto::getObject));
    }

    private DocTable createNewDocTale(CustomUserDetails customUserDetails, Document document, DocTableDto docTable) {
        DocTable newDocTable = new DocTable(UUID.randomUUID().toString(), docTable.getName(), document);
        newDocTable.setCreatedBy(customUserDetails.getUserId());
        docTableRepository.save(newDocTable);
        indicatorService.create(customUserDetails, newDocTable, docTable.getIndicators());
        return newDocTable;
    }
}
