package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.view.EquipmentView;

import java.util.List;

public interface EquipmentRepository {

    void save(Equipment equipment);

    List<EquipmentView> findAll();

}
