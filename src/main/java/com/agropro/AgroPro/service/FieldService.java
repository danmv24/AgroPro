package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.FieldWithCurrentCropView;

import java.util.List;

public interface FieldService {

    List<FieldWithCurrentCropView> getFieldsWithCropByYear(Integer year);

    void validateFieldExistsById(Long fieldId);

}
