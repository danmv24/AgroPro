package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EquipmentService {

    void addEquipment(EquipmentForm equipmentForm);

    List<EquipmentView> getAllEquipment();

    List<EquipmentBasicInfoView> getIdleEquipment();

    void validateEquipmentExistByIds(Set<Long> equipmentIds);

    void validateEquipmentStatus(Set<Long> equipmentIds);

    Map<Long, Long> getEquipmentStatusesByIds(Set<Long> equipmentIds);

    void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);
}
