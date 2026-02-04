package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.EquipmentNotFoundException;
import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.mapper.EquipmentMapper;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.service.EquipmentService;
import com.agropro.AgroPro.service.EquipmentStatusHistoryService;
import com.agropro.AgroPro.service.EquipmentTypeService;
import com.agropro.AgroPro.service.StatusService;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentService implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentTypeService equipmentTypeService;
    private final StatusService statusService;
    private final EquipmentStatusHistoryService equipmentStatusHistoryService;

    @Override
    @Transactional
    public void addEquipment(EquipmentForm equipmentForm) {
        equipmentTypeService.validateEquipmentTypeExistsById(equipmentForm.getEquipmentTypeId());
        Long idleStatusId = statusService.getIdleStatusCodeId();

        Long equipmentId = equipmentRepository.save(EquipmentMapper.toModel(equipmentForm, idleStatusId));

        equipmentStatusHistoryService.addHistoryRecord(equipmentId, idleStatusId);
    }

    @Override
    public List<EquipmentView> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public List<EquipmentBasicInfoView> getIdleEquipment() {
        return equipmentRepository.findEquipmentWithIdleStatus();
    }

    @Override
    public void validateEquipmentExistByIds(Set<Long> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            throw new IllegalArgumentException("Отправлен пустой список оборудования для проверки на существование");
        }

        Set<Long> existingIds = equipmentRepository.findExistingEquipmentByIds(equipmentIds);

        if (existingIds.size() != equipmentIds.size()) {
            Set<Long> missingIds = new HashSet<>(equipmentIds);
            missingIds.removeAll(existingIds);
            throw new EquipmentNotFoundException(HttpStatus.NOT_FOUND, missingIds);
        }
    }

    @Override
    public Map<Long, Long> getEquipmentStatusesByIds(Set<Long> equipmentIds) {
        return equipmentRepository.findEquipmentStatusesByIds(equipmentIds);
    }

    @Override
    public void validateEquipmentStatus(Set<Long> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return;
        }

        Long idleStatusId = statusService.getIdleStatusCodeId();
        Map<Long, Long> statusesById = getEquipmentStatusesByIds(equipmentIds);

        for (Long equipmentId : equipmentIds) {
            Long equipmentStatusId = statusesById.get(equipmentId);
            if (equipmentStatusId == null) {
                throw new RuntimeException("Пока что заглушка 1");
            }
            if (!equipmentStatusId.equals(idleStatusId)) {
                throw new RuntimeException("Пока что заглушка 2");
            }
        }
    }

    @Override
    public void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            return;
        }

        List<Long> conflictMachineryIds = equipmentRepository.findConflictEquipmentIdsByDateTime(equipmentIds, startDateOfWork, endDateOfWork);

        if (!conflictMachineryIds.isEmpty()) {
            throw new RuntimeException("Пока что заглушка 3");
        }
    }
}
