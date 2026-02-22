package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultFieldService implements FieldService {

    private final FieldRepository fieldRepository;

    @Override
    public List<FieldWithCurrentCropView> getFieldsWithCropByYear(Integer year) {
        return fieldRepository.findFieldsWithCropByYear(year);
    }

    @Override
    public void validateFieldExistsById(Long fieldId) {
        if (!fieldRepository.existsById(fieldId)) {
            throw new FieldNotFoundException(fieldId);
        }
    }

}
