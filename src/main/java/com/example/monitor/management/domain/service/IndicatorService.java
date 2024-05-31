package com.example.monitor.management.domain.service;

import com.example.monitor.management.api.utils.httputil.pagination.PageDTO;
import com.example.monitor.management.common.Dto.IndicatorDto;
import com.example.monitor.management.common.exceptions.ExceptionMessages;
import com.example.monitor.management.common.exceptions.OrderDuplicatedException;
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
public class IndicatorService {
    private final IndicatorRepository indicatorRepository;

    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public PageDTO<Indicator> retrieveAll(String docTableId, PageRequest pageRequest) {
        Page<Indicator> indicators = indicatorRepository.findAllByDocTableId(pageRequest, docTableId);
        return new PageDTO<>(indicators.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), indicators.getTotalElements());
    }

    public PageDTO<Indicator> retrieveUnHided(String docTableId, PageRequest pageRequest) {
        Page<Indicator> indicators = indicatorRepository.findAllUnHidedByDocTableId(pageRequest, docTableId);
        return new PageDTO<>(indicators.getContent(), pageRequest.getPageNumber(), pageRequest.getPageSize(), indicators.getTotalElements());
    }

    @Transactional
    public void create(CustomUserDetails customUserDetails, DocTable docTable, List<IndicatorDto> indicators) {
        indicators.forEach(i -> {
            Indicator indicator = createIndicator(docTable, i);
            indicator.setCreatedBy(customUserDetails.getUserId());
            docTable.addIndicator(indicator);
        });
        indicatorRepository.saveAll(docTable.getIndicators());
    }

    public void updateIndicators(CustomUserDetails customUserDetails, DocTable docTable, List<IndicatorDto> updateIndicatorDto) {
        List<Indicator> indicators = getIndicators(docTable);
        Map<String, IndicatorDto> indicatorsMap = convertIndicatorsToMap(updateIndicatorDto);
        Set<Indicator> updatedIndicators = new HashSet<>();
        indicators.forEach(i -> {
            if (indicatorsMap.containsKey(i.getId())) {
                Indicator updatedindicator = updateIndicator(customUserDetails, i, indicatorsMap.get(i.getId()));
                updatedIndicators.add(updatedindicator);
                updateIndicatorDto.remove(indicatorsMap.get(i.getId()));
            }
        });
        omitDeletedIndicators(updatedIndicators, indicators);
        docTable.setIndicators(updatedIndicators);
        if (updateIndicatorDto.size() > 0) {
            updateIndicatorDto.forEach(i -> {
                docTable.addIndicator(createIndicator(docTable, i));
            });
        }
        orderValidator(docTable.getIndicators());
        indicatorRepository.saveAll(docTable.getIndicators());
    }

    private List<Indicator> getIndicators(DocTable docTable) {
        List<Indicator> indicators = indicatorRepository.findAllByDocTableId(docTable.getId());
        if (indicators.isEmpty()) {
            throw new RecordNotFoundException(ExceptionMessages.RECORD_NOT_FOUND.getTitle());
        }
        return indicators;
    }

    private void omitDeletedIndicators(Set<Indicator> updatedIndicators, List<Indicator> currentIndicators) {
        Set<String> updatedIndicatorIds = updatedIndicators.stream().map(BaseModel::getId).collect(Collectors.toSet());
        currentIndicators.parallelStream()
                .filter(i -> !updatedIndicatorIds.contains((i.getId())))
                .forEach(indicatorRepository::delete);
    }

    private Indicator createIndicator(DocTable docTable, IndicatorDto indicatorDto) {
        return new Indicator(UUID.randomUUID().toString(),
                indicatorDto.getName(),
                indicatorDto.getOrder(),
                indicatorDto.getTransaltionFa(),
                indicatorDto.getTransaltionEn(),
                indicatorDto.getDescriptionFa(),
                indicatorDto.getDescriptionEn(),
                indicatorDto.getDataType(),
                indicatorDto.getIndicatorType(),
                indicatorDto.getUnitType(),
                indicatorDto.getComputation(),
                docTable
        );

    }

    private Indicator updateIndicator(CustomUserDetails customUserDetails, Indicator indicator, IndicatorDto updateIndicatorDto) {
        indicator.setUpdatedBy(customUserDetails.getUserId());
        indicator.setName(updateIndicatorDto.getName());
        indicator.setOrder(updateIndicatorDto.getOrder());
        indicator.setTransaltionEn(updateIndicatorDto.getTransaltionEn());
        indicator.setTransaltionFa(updateIndicatorDto.getTransaltionFa());
        indicator.setDescriptionEn(updateIndicatorDto.getDescriptionEn());
        indicator.setDescriptionFa(updateIndicatorDto.getDescriptionFa());
        indicator.setIndicatorType(updateIndicatorDto.getIndicatorType());
        indicator.setDataType(updateIndicatorDto.getDataType());
        indicator.setUnitType(updateIndicatorDto.getUnitType());
        indicator.setComputation(updateIndicatorDto.getComputation());
        indicator.visible(updateIndicatorDto.isHided());
        return indicator;

    }

    private Map<String, IndicatorDto> convertIndicatorsToMap(List<IndicatorDto> updateIndicatorDto) {
        return updateIndicatorDto.stream()
                .collect(Collectors.toMap(IndicatorDto::getId, IndicatorDto::getObject));
    }

    private void orderValidator(Set<Indicator> indicators) {
        Map<Integer, Long> orderCont = indicators.stream()
                .collect(Collectors.groupingBy(Indicator::getOrder, Collectors.counting()));
        orderCont.forEach((key, value) -> {
            if (value > 1) {
                throw new OrderDuplicatedException("Orders must be unique");
            }
        });

    }
}
