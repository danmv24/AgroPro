package com.agropro.AgroPro.service;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.form.EquipmentUpdateForm;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EquipmentService {

    void createEquipment(EquipmentForm equipmentForm);

    List<EquipmentView> getAllEquipment();

    List<EquipmentBasicInfoView> getIdleEquipment();

    void validateEquipmentExistByIds(Set<Long> equipmentIds);

//    void validateEquipmentStatus(Set<Long> equipmentIds);

    void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EquipmentBasicInfoView> getEquipmentByWorkId(Long workId);

    void changeEquipmentStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateEquipment(Long id, EquipmentUpdateForm equipmentUpdateForm);
}
