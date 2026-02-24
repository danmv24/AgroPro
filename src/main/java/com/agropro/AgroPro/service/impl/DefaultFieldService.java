package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.mapper.FieldMapper;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldView;
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
    public List<FieldView> getFieldsWithCropByDate(LocalDate date) {
        List<Field> fields = fieldRepository.findAll();

        Set<Long> fieldIds = fields.stream()
                .map(Field::getId)
                .collect(Collectors.toSet());

        Map<Long, FieldPlanting> plantingsByFieldId = fieldPlantingService.getPlantingsByIdsAndDate(fieldIds, date).stream()
                .collect(Collectors.toMap(
                        FieldPlanting::getFieldId,
                        Function.identity()
                ));

        return fields.stream()
                .map(field -> {
                    FieldPlanting planting = plantingsByFieldId.get(field.getId());

                    return FieldMapper.toView(field, planting);
                }).toList();

    }

    @Override
    public void validateFieldExistsById(Long fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new FieldNotFoundException(fieldId);
        }
    }

    @Override
    public List<Field> getFieldsByIds(Set<Long> fieldIds) {
        return fieldRepository.findAllById(fieldIds);
    }

    @Override
    public Field getFieldById(Long id) {
        return fieldRepository.findById(id).orElseThrow(() -> new FieldNotFoundException(id));
    }

}
