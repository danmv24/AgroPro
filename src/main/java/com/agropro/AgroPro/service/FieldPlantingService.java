package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.view.FieldPlantingView;

import java.util.List;

public interface FieldPlantingService {

    void addFieldPlanting(FieldPlantingForm fieldForm);

    List<FieldPlantingView> getFieldPlantingsByFieldId(Long fieldId);

}
