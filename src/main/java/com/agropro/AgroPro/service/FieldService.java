package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;

import java.util.List;

public interface FieldService {

    void addFieldPlanting(FieldPlantingForm fieldForm);

    List<FieldWithCurrentCropView> getFieldsWithCropByYear(Integer year);

}
