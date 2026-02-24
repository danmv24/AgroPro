package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.mapper.FieldPlantingMapper;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultFieldPlantingService implements FieldPlantingService {

    private final FieldService fieldService;
    private final FieldPlantingRepository fieldPlantingRepository;

    @Override
    public void createFieldPlanting(Long fieldId, String cropType, LocalDate plantingDate) {
        fieldService.validateFieldExistsById(fieldId);

        CropType crop = CropType.fromString(cropType);
        fieldPlantingRepository.save(FieldPlantingMapper.toModel(fieldId, crop, plantingDate));
    }

    @Override
    public void addHarvestDate(Long fieldId, LocalDate harvestDate) {
        fieldService.validateFieldExistsById(fieldId);

        FieldPlanting fieldPlanting = fieldPlantingRepository.findFieldPlantingByFieldId(fieldId).orElseThrow(() ->
                new IllegalStateException("Нет активного посева для данного поля"));

        fieldPlanting.setHarvestDate(harvestDate);

        fieldPlantingRepository.save(fieldPlanting);
    }

    @Override
    public List<FieldPlanting> getPlantingsByIdsAndDate(Set<Long> fieldIds, LocalDate date) {
        return fieldPlantingRepository.findAllByIdAndDate(fieldIds, date);
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
