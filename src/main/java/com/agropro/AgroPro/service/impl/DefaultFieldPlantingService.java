package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.mapper.FieldPlantingMapper;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.service.CropService;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldPlantingView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultFieldPlantingService implements FieldPlantingService {

    private final FieldPlantingRepository fieldPlantingRepository;
    private final FieldService fieldService;
    private final CropService cropService;

    @Override
    @Transactional
    public void addFieldPlanting(FieldPlantingForm fieldPlantingForm) {
        fieldService.validateFieldExistsById(fieldPlantingForm.getFieldId());
        cropService.validateCropExistsById(fieldPlantingForm.getCropId());

        fieldPlantingRepository.save(FieldPlantingMapper.toModel(fieldPlantingForm));
    }

    @Override
    public List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId) {
        return fieldPlantingRepository.findPlantingsByFieldId(fieldId);
    }

}
