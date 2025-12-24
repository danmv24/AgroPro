package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.CropNotFoundException;
import com.agropro.AgroPro.exception.FieldNotFoundException;
import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.mapper.FieldPlantingMapper;
import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultFieldService implements FieldService {

    private final FieldRepository fieldRepository;
    private final CropRepository cropRepository;
    private final FieldPlantingRepository fieldPlantingRepository;

    @Override
    public void addFieldPlanting(FieldPlantingForm fieldPlantingForm) {
        validateFieldExists(fieldPlantingForm.getFieldId());
        validateCropExists(fieldPlantingForm.getCropId());

        fieldPlantingRepository.save(FieldPlantingMapper.toModel(fieldPlantingForm));
    }

    @Override
    public List<FieldWithCurrentCropView> getFieldsWithCropByYear(Integer year) {
        return fieldRepository.findFieldsWithCropByYear(year);
    }

    private void validateFieldExists(Long fieldId) {
        if (!fieldRepository.existsByFieldId(fieldId)) {
            throw new FieldNotFoundException(HttpStatus.NOT_FOUND, fieldId);
        }
    }

    private void validateCropExists(Long cropId) {
        if (!cropRepository.existsByCropId(cropId)) {
            throw new CropNotFoundException(HttpStatus.NOT_FOUND, cropId);
        }
    }



}
