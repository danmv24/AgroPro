package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.view.FieldView;

import java.util.List;

public interface FieldRepository {

    List<FieldView> findAll();

    boolean existsByFieldId(Long fieldId);

}
