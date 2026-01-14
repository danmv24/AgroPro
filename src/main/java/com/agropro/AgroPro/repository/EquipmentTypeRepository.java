package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.EquipmentType;

import java.util.List;

public interface EquipmentTypeRepository {

    List<EquipmentType> findAll();

    boolean existsById(Long id);

}
