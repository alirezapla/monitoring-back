package com.example.monitor.management.domain.service;

import com.example.monitor.management.common.Dto.IndicatorComputationDto;
import com.example.monitor.management.common.Dto.IndicatorDto;
import com.example.monitor.management.common.exceptions.OrderDuplicatedException;
import com.example.monitor.management.domain.model.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndicatorService {
    private final IndicatorRepository indicatorRepository;
    private final ComputationRepository computationRepository;

    public IndicatorService(IndicatorRepository indicatorRepository, ComputationRepository computationRepository) {
        this.indicatorRepository = indicatorRepository;
        this.computationRepository = computationRepository;
    }


    @Transactional
    public void create(UserDetails customUserDetails, DocTable docTable, List<IndicatorDto> indicators) {
        indicators.forEach(i -> {
            Indicator indicator = createIndicator(docTable, i);
            indicator.setCreatedBy(customUserDetails.getUsername());
            docTable.addIndicator(indicator);
        });
    }

    @Transactional
    public void updateIndicators(UserDetails customUserDetails, DocTable docTable, List<IndicatorDto> updateIndicatorDto) {
        Map<String, IndicatorDto> indicatorsMap = convertIndicatorsToMap(updateIndicatorDto);
        Set<Indicator> updatedIndicators = new HashSet<>();
        docTable.getIndicators().forEach(i -> {
            if (indicatorsMap.containsKey(i.getId())) {
                Indicator updatedindicator = updateIndicator(customUserDetails, i, indicatorsMap.get(i.getId()));
                updatedIndicators.add(updatedindicator);
                updateIndicatorDto.remove(indicatorsMap.get(i.getId()));
            }
        });
        omitDeletedIndicators(updatedIndicators, docTable.getIndicators());
        docTable.setIndicators(updatedIndicators);
        if (updateIndicatorDto.size() > 0) {
            updateIndicatorDto.forEach(i -> {
                docTable.addIndicator(createIndicator(docTable, i));
            });
        }
        orderValidator(docTable.getIndicators());
    }


    private void omitDeletedIndicators(Set<Indicator> updatedIndicators, Set<Indicator> currentIndicators) {
        Set<String> updatedIndicatorIds = updatedIndicators.stream().map(BaseModel::getId).collect(Collectors.toSet());
        currentIndicators.parallelStream()
                .filter(i -> !updatedIndicatorIds.contains((i.getId())))
                .forEach(indicatorRepository::delete);
    }

    private Indicator createIndicator(DocTable docTable, IndicatorDto indicatorDto) {
        Indicator indicator = new Indicator(UUID.randomUUID().toString(),
                indicatorDto.getName(),
                indicatorDto.getOrder(),
                indicatorDto.getTranslationFa(),
                indicatorDto.getTranslationEn(),
                indicatorDto.getDescriptionFa(),
                indicatorDto.getDescriptionEn(),
                DataType.valueOf(indicatorDto.getDataType()),
                IndicatorType.valueOf(indicatorDto.getIndicatorType()),
                UnitType.valueOf(indicatorDto.getUnitType()),
                docTable
        );
        return indicator.insertComputations(
                mapComputationsDtoToComputations(indicator, indicatorDto.getComputations()));
    }

    private Set<Computation> mapComputationsDtoToComputations(Indicator indicator, Set<IndicatorComputationDto> indicatorComputationDto) {
        Set<Computation> computations = new HashSet<>();
        indicatorComputationDto.forEach(i -> {
            computations.add(new Computation(i.getLabel(), i.getDescription(), indicator));
        });
        return computations;
    }

    private Indicator updateIndicator(UserDetails customUserDetails, Indicator indicator, IndicatorDto updateIndicatorDto) {
        indicator.setUpdatedBy(customUserDetails.getUsername());
        indicator.setName(updateIndicatorDto.getName());
        indicator.setOrder(updateIndicatorDto.getOrder());
        indicator.setTranslationEn(updateIndicatorDto.getTranslationEn());
        indicator.setTranslationFa(updateIndicatorDto.getTranslationFa());
        indicator.setDescriptionEn(updateIndicatorDto.getDescriptionEn());
        indicator.setDescriptionFa(updateIndicatorDto.getDescriptionFa());
        indicator.setIndicatorType(IndicatorType.valueOf(updateIndicatorDto.getIndicatorType()));
        indicator.setDataType(DataType.valueOf(updateIndicatorDto.getDataType()));
        indicator.setUnitType(UnitType.valueOf(updateIndicatorDto.getUnitType()));
        indicator.visible(updateIndicatorDto.isHided());
        updateIndicatorComputations(indicator, updateIndicatorDto.getComputations());

        return indicator;

    }

    private void updateIndicatorComputations(Indicator indicator, Set<IndicatorComputationDto> indicatorComputationsDto) {
        Set<Computation> updatedComputations = new HashSet<>();
        Map<String, IndicatorComputationDto> indicatorComputationDtoMap = indicatorComputationsDto.stream()
                .collect(Collectors.toMap(IndicatorComputationDto::getId, IndicatorComputationDto::getObject));

        indicator.getComputations().forEach(i -> {
            IndicatorComputationDto indicatorComputation = indicatorComputationDtoMap.get(i.getId());
            if (indicatorComputationDtoMap.containsKey(i.getId())) {
                i.setDescription(indicatorComputation.getDescription());
                i.setLabel(indicatorComputation.getLabel());
                updatedComputations.add(i);
                indicatorComputationsDto.remove(indicatorComputationDtoMap.get(i.getId()));
            }
        });
        omitDeletedComputation(updatedComputations, indicator.getComputations());
        indicator.setComputations(updatedComputations);
        if (indicatorComputationsDto.size() > 0) {
            indicatorComputationsDto.forEach(i -> {
                indicator.addComputation(new Computation(i.getLabel(), i.getDescription(),indicator));
            });
        }
    }

    private void omitDeletedComputation(Set<Computation> updatedComputations, Set<Computation> computations) {
        Set<String> updatedComputationIds = updatedComputations.stream().map(Computation::getId).collect(Collectors.toSet());
        computations.parallelStream()
                .filter(i -> !updatedComputationIds.contains((i.getId())))
                .forEach(computationRepository::delete);
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
