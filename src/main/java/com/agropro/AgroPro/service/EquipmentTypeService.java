package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.EquipmentTypeView;

import java.util.List;

public interface EquipmentTypeService {

    List<EquipmentTypeView> getAllEquipmentTypes();

    void validateEquipmentTypeExistsById(Long id);

}
