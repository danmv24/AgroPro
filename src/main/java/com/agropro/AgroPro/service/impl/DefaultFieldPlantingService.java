package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.mapper.FieldPlantingMapper;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
import lombok.RequiredArgsConstructor;
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
    public void createFieldPlanting(Long fieldId, String cropType, LocalDate plantingDate) {
        validateFieldExistsById(fieldId);

        CropType crop = CropType.fromString(cropType);
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
    public List<FieldPlanting> getPlantingsByIdsAndDate(Set<Long> fieldIds, LocalDate date) {
        return fieldPlantingRepository.findAllByIdAndDate(fieldIds, date);
    }

    private void validateFieldExistsById(Long fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new FieldNotFoundException(fieldId);
        }
    }

//    @Override
//    public List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId) {
//        List<FieldPlanting> fieldPlantings = fieldPlantingRepository.findFieldPlantingByFieldId(fieldId);
//
//        return fieldPlantings.stream()
//                .map(FieldPlantingMapper::toView)
//                .toList();
//    }

}
