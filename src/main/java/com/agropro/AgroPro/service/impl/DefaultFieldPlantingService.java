package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.controller.FieldPlantingResponse;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.mapper.FieldPlantingMapper;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultFieldPlantingService implements FieldPlantingService {

    private final FieldPlantingRepository fieldPlantingRepository;
    private final FieldRepository fieldRepository;

    @Override
    public void createFieldPlanting(Long fieldId, CropType crop, LocalDate plantingDate) {
        validateFieldExistsById(fieldId);

        fieldPlantingRepository.save(FieldPlantingMapper.toModel(fieldId, crop, plantingDate));
    }

    @Override
    public void addHarvestDate(Long fieldId, LocalDate harvestDate) {
        validateFieldExistsById(fieldId);

        FieldPlanting fieldPlanting = fieldPlantingRepository.findFieldPlantingByFieldId(fieldId).orElseThrow(() ->
                new IllegalStateException("Нет активного посева для данного поля"));

        fieldPlanting.setHarvestDate(harvestDate);

        fieldPlantingRepository.save(fieldPlanting);
    }

    @Override
    public List<FieldPlantingInternalData> getPlantingsByIdsAndDate(Set<Long> fieldIds, LocalDate date) {
        List<FieldPlanting> fieldPlantings = fieldPlantingRepository.findAllByFieldIdsAndDate(fieldIds, date);

        return fieldPlantings.stream()
                .map(FieldPlantingMapper::toInternalData)
                .toList();
    }

    @Override
    public Slice<FieldPlantingResponse> getFieldPlantingHistory(Long fieldId, int page, int size) {
        validateFieldExistsById(fieldId);
        Pageable pageable = PageRequest.of(page, size);

        Slice<FieldPlanting> fieldPlantings = fieldPlantingRepository.findByFieldIdOrderByPlantingDateDesc(fieldId, pageable);

        return fieldPlantings.map(FieldPlantingMapper::toResponse);
    }

    private void validateFieldExistsById(Long fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new FieldNotFoundException(fieldId);
        }
    }

}
