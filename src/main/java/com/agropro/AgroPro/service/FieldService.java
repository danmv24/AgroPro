package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.FieldInternalData;
import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface FieldService {

    List<FieldResponse> getFieldsWithCropByDate(LocalDate date);

    void validateFieldExistsById(Long fieldId);

    List<FieldInternalData> getFieldsByIds(Set<Long> fieldIds);

    FieldInternalData getFieldById(Long id);

    List<FieldBasicInfoResponse> getFields();

}
