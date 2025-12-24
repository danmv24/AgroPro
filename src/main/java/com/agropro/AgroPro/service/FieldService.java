package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.view.FieldPlantingView;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;

import java.util.List;

public interface FieldService {

    void addFieldPlanting(FieldPlantingForm fieldForm);

    List<FieldWithCurrentCropView> getFieldsWithCropByYear(Integer year);

    List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId);

}
