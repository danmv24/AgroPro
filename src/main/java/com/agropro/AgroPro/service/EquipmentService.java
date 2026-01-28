package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;

import java.util.List;

public interface EquipmentService {

    void addEquipment(EquipmentForm equipmentForm);

    List<EquipmentView> getAllEquipment();

    List<EquipmentBasicInfoView> getIdleEquipment();

}
