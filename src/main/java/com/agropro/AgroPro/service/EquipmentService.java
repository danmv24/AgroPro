package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.EquipmentRequest;
import com.agropro.AgroPro.dto.request.EquipmentUpdateRequest;
import com.agropro.AgroPro.dto.response.EquipmentBasicInfoResponse;
import com.agropro.AgroPro.dto.response.EquipmentResponse;
import com.agropro.AgroPro.enums.StatusCode;
import org.springframework.data.domain.Slice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EquipmentService {

    void createEquipment(EquipmentRequest equipmentRequest);

    Slice<EquipmentResponse> getAllEquipment(int page, int size);

    void validateEquipmentExistByIds(Set<Long> equipmentIds);

    void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork);

    List<EquipmentBasicInfoResponse> getEquipmentByWorkId(Long workId);

    void changeEquipmentStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt);

    void updateEquipment(Long id, EquipmentUpdateRequest equipmentUpdateRequest);
}
