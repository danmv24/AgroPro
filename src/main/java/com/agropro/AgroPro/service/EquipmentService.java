package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.EquipmentForm;
import com.agropro.AgroPro.dto.request.EquipmentUpdateForm;
import com.agropro.AgroPro.dto.response.EquipmentBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EquipmentResponse;
import com.agropro.AgroPro.enums.StatusCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EquipmentService {

    void createEquipment(EquipmentForm equipmentForm);

    List<EquipmentResponse> getAllEquipment();

    List<EquipmentBasicInfoResponse> getIdleEquipment();

    void validateEquipmentExistByIds(Set<Long> equipmentIds);

//    void validateEquipmentStatus(Set<Long> equipmentIds);

    void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EquipmentBasicInfoResponse> getEquipmentByWorkId(Long workId);

    void changeEquipmentStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateEquipment(Long id, EquipmentUpdateForm equipmentUpdateForm);
}
