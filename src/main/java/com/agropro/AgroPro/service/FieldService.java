package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.view.FieldView;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FieldService {

    List<FieldView> getFieldsWithCropByDate(LocalDate date);

    void validateFieldExistsById(Long fieldId);

    List<Field> getFieldsByIds(Set<Long> fieldIds);

    Field getFieldById(Long id);

}
