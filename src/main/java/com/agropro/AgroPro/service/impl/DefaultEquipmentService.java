package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.exception.*;
import com.agropro.AgroPro.form.EquipmentForm;
import com.agropro.AgroPro.form.EquipmentUpdateForm;
import com.agropro.AgroPro.mapper.EquipmentMapper;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.service.EquipmentService;
import com.agropro.AgroPro.service.EquipmentStatusHistoryService;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentService implements EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final EquipmentStatusHistoryService equipmentStatusHistoryService;

    @Override
    @Transactional
    public void createEquipment(EquipmentForm equipmentForm) {
        LocalDateTime changedAt = LocalDateTime.now();
        Equipment equipment = equipmentRepository.save(EquipmentMapper.toModel(equipmentForm));

        equipmentStatusHistoryService.createHistoryRecord(equipment, changedAt);
    }

    @Override
    public List<EquipmentView> getAllEquipment() {
        List<Equipment> equipment = equipmentRepository.findAll();

        return equipment.stream()
                .map(EquipmentMapper::toView)
                .toList();
    }

    @Override
    public List<EquipmentBasicInfoView> getIdleEquipment() {
        List<Equipment> equipment = equipmentRepository.findEquipmentByCurrentStatus(StatusCode.IDLE);

        return equipment.stream()
                .map(EquipmentMapper::toBasicInfoView)
                .toList();
    }

    @Override
    public void validateEquipmentExistByIds(Set<Long> equipmentIds) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        Set<Long> existingIds = equipmentRepository.findEquipmentIdsByIdIn(equipmentIds);

        if (existingIds.size() != equipmentIds.size()) {
            Set<Long> missingIds = new HashSet<>(equipmentIds);
            missingIds.removeAll(existingIds);
            throw new EquipmentNotFoundException(missingIds);
        }
    }

    @Override
    public void validateEquipmentAvailability(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (equipmentIds == null || equipmentIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        List<Equipment> equipment = equipmentRepository.findAllByIdIn(equipmentIds);
        Set<Long> foundIds = equipment.stream()
                .map(Equipment::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = equipmentIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new EquipmentNotFoundException(missingIds);
        }

        List<Equipment> equipmentWithInvalidStatus = equipment.stream()
                .filter(e -> e.getCurrentStatus() == StatusCode.UNDER_REPAIR ||
                        e.getCurrentStatus() == StatusCode.DECOMMISSIONED)
                .toList();

        if (!equipmentWithInvalidStatus.isEmpty()) {
            List<Long> equipmentIdsWithInvalidStatus = equipmentWithInvalidStatus.stream().map(Equipment::getId).toList();
            List<Integer> inventoryNumbersWithInvalidStatus = equipmentWithInvalidStatus.stream().map(Equipment::getInventoryNumber).toList();
            throw new InvalidEquipmentStatusException(equipmentIdsWithInvalidStatus, inventoryNumbersWithInvalidStatus);
        }

        List<WorkStatus> workStatuses = List.of(WorkStatus.PLANNED, WorkStatus.IN_PROGRESS);
        List<Equipment> conflictEquipment = equipmentRepository.findConflictEquipmentByStartDateAndEndDate(equipmentIds, workStatuses,
                Timestamp.valueOf(startDateOfWork), Timestamp.valueOf(endDateOfWork));

        if (!conflictEquipment.isEmpty()) {
            List<Long> conflictEquipmentIds = conflictEquipment.stream().map(Equipment::getId).toList();
            List<Integer> conflictInventoryNumbers = conflictEquipment.stream().map(Equipment::getInventoryNumber).toList();
            throw new EquipmentNotAvailableException(conflictEquipmentIds, conflictInventoryNumbers);
        }
    }

    @Override
    public List<EquipmentBasicInfoView> getEquipmentByWorkId(Long workId) {
        List<Equipment> equipment = equipmentRepository.findEquipmentByWorkId(workId);

        return equipment.stream()
                .map(EquipmentMapper::toBasicInfoView)
                .toList();
    }

    @Override
    @Transactional
    public void changeEquipmentStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt) {
        if (workIds == null || workIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        List<Equipment> equipment = equipmentRepository.findAllByWorkIds(workIds);
        if (equipment.isEmpty()) {
            return;
        }

        List<StatusCode> allowedStatuses = List.of(StatusCode.IDLE, StatusCode.IN_OPERATION);

        Set<Equipment> conflictingEquipment = equipment.stream()
                .filter(e -> !allowedStatuses.contains(e.getCurrentStatus()))
                .collect(Collectors.toSet());

        if (!conflictingEquipment.isEmpty()) {
            Set<Long> equipmentIds = conflictingEquipment.stream().map(Equipment::getId).collect(Collectors.toSet());
            throw new EquipmentStatusConflictException(equipmentIds, statusCode);
        }

        equipment.forEach(e -> e.setCurrentStatus(statusCode));
        equipmentRepository.saveAll(equipment);
        equipmentStatusHistoryService.createAllHistoryRecords(equipment, changedAt);
    }

    @Override
    @Transactional
    public void updateEquipment(Long id, EquipmentUpdateForm equipmentUpdateForm) {
        Equipment equipment = equipmentRepository.findById(id).orElseThrow(() -> new EquipmentNotFoundException(Set.of(id)));

        if (equipment.getCurrentStatus() == StatusCode.DECOMMISSIONED) {
            throw new EquipmentCannotBeModifiedException(equipment.getId());
        }

        StatusCode newStatusCode = equipmentUpdateForm.getStatus();

        equipment.setEquipmentName(equipmentUpdateForm.getEquipmentName());
        equipment.setEquipmentType(equipmentUpdateForm.getEquipmentType());
        equipment.setInventoryNumber(equipmentUpdateForm.getInventoryNumber());
        equipment.setPurchaseDate(equipmentUpdateForm.getPurchaseDate());

        if (equipment.getCurrentStatus() != newStatusCode) {
            equipment.setCurrentStatus(newStatusCode);
            equipmentStatusHistoryService.createHistoryRecord(equipment, LocalDateTime.now());
        }

        equipmentRepository.save(equipment);

    }
}
