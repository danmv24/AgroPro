package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.dto.internal.FieldInternalData;
import com.agropro.AgroPro.dto.internal.FieldPlantingInternalData;
import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.mapper.FieldMapper;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultFieldService implements FieldService {

    private final FieldRepository fieldRepository;
    private final FieldPlantingService fieldPlantingService;

    @Override
    public List<FieldResponse> getFieldsWithCropByDate(LocalDate date) {
        List<Field> fields = fieldRepository.findAll();

        Set<Long> fieldIds = fields.stream()
                .map(Field::getId)
                .collect(Collectors.toSet());

        Map<Long, FieldPlantingInternalData> plantingsByFieldId = fieldPlantingService.getPlantingsByIdsAndDate(fieldIds, date).stream()
                .collect(Collectors.toMap(
                        FieldPlantingInternalData::getFieldId,
                        Function.identity()
                ));

        return fields.stream()
                .map(field -> {
                    FieldPlantingInternalData planting = plantingsByFieldId.get(field.getId());

                    return FieldMapper.toResponse(field, planting);
                }).toList();

    }

    @Override
    public void validateFieldExistsById(Long fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new FieldNotFoundException(fieldId);
        }
    }

    @Override
    public List<FieldInternalData> getFieldsByIds(Set<Long> fieldIds) {
        List<Field> fields = fieldRepository.findAllById(fieldIds);

        return fields.stream()
                .map(FieldMapper::toInternalData)
                .toList();
    }

    @Override
    public FieldInternalData getFieldById(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new FieldNotFoundException(id));

        return FieldMapper.toInternalData(field);
    }

    @Override
    public List<FieldBasicInfoResponse> getFields() {
        List<Field> fields = fieldRepository.findAll();

        return fields.stream()
                .map(FieldMapper::toBasicInfoResponse)
                .toList();
    }

}
