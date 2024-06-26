package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PageDTO;
import com.example.monitor.management.common.Dto.BodyDto;
import com.example.monitor.management.common.Dto.ComputingTableItemsDto;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.RecordNotFoundException;
import com.example.monitor.management.domain.model.ComputingTableItems;
import com.example.monitor.management.domain.model.ComputingTableItemsRepository;
import com.example.monitor.management.domain.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComputingTableService {
    private final ComputingTableItemsRepository computingTableItemsRepository;

    public ComputingTableService(ComputingTableItemsRepository computingTableItemsRepository) {
        this.computingTableItemsRepository = computingTableItemsRepository;
    }

    public Set<ComputingTableItems> create(Document document, BodyDto bodyDto) {
        Set<ComputingTableItems> computingTableItems = new HashSet<>();
        bodyDto.getComputingTableItems().forEach(i -> {
            computingTableItems.add(new ComputingTableItems(
                    UUID.randomUUID().toString(), i.getCommand(), i.getDescription(), document));
        });
        computingTableItemsRepository.saveAll(computingTableItems);
        return computingTableItems;
    }

    public PageDTO<ComputingTableItems> retrieveAll(String docId, PageRequest pageRequest) {
        Page<ComputingTableItems> computingTableItems = computingTableItemsRepository.findAllByDocumentId(pageRequest, docId);
        return new PageDTO<>(computingTableItems.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), computingTableItems.getTotalElements());

    }

    public PageDTO<ComputingTableItems> retrieveUnHided(String docId, PageRequest pageRequest) {
        Page<ComputingTableItems> computingTableItems = computingTableItemsRepository.findAllUnHidedByDocumentId(pageRequest, docId);
        return new PageDTO<>(computingTableItems.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), computingTableItems.getTotalElements());

    }

    public Set<ComputingTableItems> update(Document document, BodyDto bodyDto) {
        Map<String, ComputingTableItemsDto> itemsMap = bodyDto.getComputingTableItems().stream()
                .collect(Collectors.toMap(ComputingTableItemsDto::getId, ComputingTableItemsDto::getObject));
        List<ComputingTableItems> computingTable = computingTableItemsRepository.findByDocumentId(document.getId());
        if (computingTable.isEmpty()) {
            throw new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle());
        }
        Set<ComputingTableItems> updatedComputingTableItems = new HashSet<>();
        computingTable.forEach(i -> {
            if (itemsMap.containsKey(i.getId())) {
                ComputingTableItemsDto computingTableItemsDto = itemsMap.get(i.getId());
                i.setDescription(computingTableItemsDto.getDescription());
                i.setCommand(computingTableItemsDto.getCommand());
                i.visible(computingTableItemsDto.isHided());
                updatedComputingTableItems.add(i);
                bodyDto.getComputingTableItems().remove(computingTableItemsDto);
                computingTableItemsRepository.save(i);
            }
        });
        omitDeletedComputingItems(updatedComputingTableItems, computingTable);
        document.setComputingTableItems(updatedComputingTableItems);
        if (bodyDto.getComputingTableItems().size() > 0) {
            bodyDto.getComputingTableItems().forEach(i -> {
                document.addComputingItem(new ComputingTableItems(
                        UUID.randomUUID().toString(), i.getCommand(), i.getDescription(), document));
            });
        }
        computingTableItemsRepository.saveAll(updatedComputingTableItems);
        return updatedComputingTableItems;

    }

    private void omitDeletedComputingItems(Set<ComputingTableItems> updatedItems, List<ComputingTableItems> currentItems) {
        Set<String> updatedItemsId = updatedItems.stream().map(ComputingTableItems::getId).collect(Collectors.toSet());
        currentItems.parallelStream()
                .filter(i -> !updatedItemsId.contains((i.getId())))
                .forEach(computingTableItemsRepository::delete);
    }


}
