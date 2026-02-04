package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EquipmentRepository {

    Long save(Equipment equipment);

    List<EquipmentView> findAll();

    List<EquipmentBasicInfoView> findEquipmentWithIdleStatus();

    Set<Long> findExistingEquipmentByIds(Set<Long> equipmentIds);

    Map<Long, Long> findEquipmentStatusesByIds(Set<Long> equipmentIds);

    List<Long> findConflictEquipmentIdsByDateTime(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

}
