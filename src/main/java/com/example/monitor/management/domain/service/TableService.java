package com.example.monitor.management.domain.service;

import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.Dto.DocTableDto;
import com.example.monitor.management.domain.model.*;
import org.springframework.security.core.userdetails.UserDetails;
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


    @Transactional
    public Set<DocTable> createTable(UserDetails customUserDetails, Document document, BodyDto createBodyDto) {
        Set<DocTable> docTables = new HashSet<>();
        createBodyDto.getDocTableDto().forEach(d -> {
            docTables.add(createNewDocTale(customUserDetails, document, d));
        });
        return docTables;
    }

    public Set<DocTable> update(UserDetails customUserDetails, Document document, Set<DocTableDto> updateTableDto) {
        Map<String, DocTableDto> dtoTableMap = convertDocTableToMap(updateTableDto);
        Set<DocTable> updatedDocTables = new HashSet<>();
        document.getDocTables().forEach(d -> {
            if (dtoTableMap.containsKey(d.getId())) {
                DocTableDto tableDto = dtoTableMap.get(d.getId());
                d.setName(tableDto.getName());
                d.visible(tableDto.isHided());
                d.setUpdatedBy(customUserDetails.getUsername());
                updatedDocTables.add(d);
                updateTableDto.remove(tableDto);
                indicatorService.updateIndicators(customUserDetails, d, tableDto.getIndicators());
            }
        });
        omitDeletedTables(updatedDocTables, document.getDocTables());
        if (updateTableDto.size() > 0) {
            updateTableDto.forEach(i -> {
                updatedDocTables.add(createNewDocTale(customUserDetails, document, i));
            });
        }
        return updatedDocTables;
    }


    private void omitDeletedTables(Set<DocTable> updatedDocTables, Set<DocTable> currentDocTables) {
        Set<String> updatedDocTablesIds = updatedDocTables.stream().map(BaseModel::getId).collect(Collectors.toSet());
        currentDocTables.parallelStream().filter(i -> !updatedDocTablesIds.contains((i.getId()))).forEach(docTableRepository::delete);
    }

    private Map<String, DocTableDto> convertDocTableToMap(Set<DocTableDto> updateTableDto) {
        return updateTableDto.stream().collect(Collectors.toMap(DocTableDto::getId, DocTableDto::getObject));
    }

    private DocTable createNewDocTale(UserDetails customUserDetails, Document document, DocTableDto docTable) {
        DocTable newDocTable = new DocTable(UUID.randomUUID().toString(), docTable.getName(), document);
        newDocTable.setCreatedBy(customUserDetails.getUsername());
        indicatorService.create(customUserDetails, newDocTable, docTable.getIndicators());
        return newDocTable;
    }
}
