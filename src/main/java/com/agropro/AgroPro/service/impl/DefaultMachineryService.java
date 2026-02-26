package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.exception.*;
import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.form.MachineryUpdateForm;
import com.agropro.AgroPro.mapper.MachineryMapper;
import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.service.MachineryService;
import com.agropro.AgroPro.service.MachineryStatusHistoryService;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;
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
public class DefaultMachineryService implements MachineryService {

    private final MachineryStatusHistoryService machineryStatusHistoryService;
    private final MachineryRepository machineryRepository;

    @Transactional
    @Override
    public void createMachinery(MachineryForm machineryForm) {
        LocalDateTime now = LocalDateTime.now();
        Machinery machinery = machineryRepository.save(MachineryMapper.toModel(machineryForm));

        machineryStatusHistoryService.createHistoryRecord(machinery, now);
    }

    @Override
    public List<MachineryView> getMachineries() {
        List<Machinery> machineries = machineryRepository.findAll();

        return machineries.stream()
                .map(MachineryMapper::toView)
                .toList();
    }

    @Override
    public void validateMachineriesExistByIds(Set<Long> machineryIds) {
        if (machineryIds == null || machineryIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        Set<Long> existingIds = machineryRepository.findMachineryIdsByMachineryIdIn(machineryIds);

        if (existingIds.size() != machineryIds.size()) {
            Set<Long> missingIds = new HashSet<>(machineryIds);
            missingIds.removeAll(existingIds);
            throw new MachineryNotFoundException(missingIds);
        }
    }

    @Override
    public void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (machineryIds == null || machineryIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        List<Machinery> machineries = machineryRepository.findAllByIdIn(machineryIds);
        Set<Long> foundIds = machineries.stream()
                .map(Machinery::getId)
                .collect(Collectors.toSet());

        Set<Long> missingIds = machineryIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new MachineryNotFoundException(missingIds);
        }

        List<Machinery> machineriesWithInvalidStatus = machineries.stream()
                .filter(machinery -> machinery.getCurrentStatus() == StatusCode.DECOMMISSIONED ||
                        machinery.getCurrentStatus() == StatusCode.UNDER_REPAIR)
                .toList();

        if (!machineriesWithInvalidStatus.isEmpty()) {
            List<Long> machineryIdsWithInvalidStatus = machineriesWithInvalidStatus.stream().map(Machinery::getId).toList();
            List<Integer> inventoryNumbers = machineriesWithInvalidStatus.stream().map(Machinery::getInventoryNumber).toList();
            throw new InvalidMachineryStatusException(machineryIdsWithInvalidStatus, inventoryNumbers);
        }

        List<WorkStatus> workStatuses = List.of(WorkStatus.PLANNED, WorkStatus.IN_PROGRESS);
        List<Machinery> conflictMachinery = machineryRepository.findConflictMachineryByStartDateAndEndDate(machineryIds,
                workStatuses, Timestamp.valueOf(startDateOfWork), Timestamp.valueOf(endDateOfWork));

        if (!conflictMachinery.isEmpty()) {
            List<Long> conflictMachineryIds = conflictMachinery.stream().map(Machinery::getId).toList();
            List<Integer> conflictInventoryNumbers = conflictMachinery.stream().map(Machinery::getInventoryNumber).toList();
            throw new MachineryNotAvailableException(conflictMachineryIds, conflictInventoryNumbers);
        }
    }

    @Override
    public List<MachineryBasicInfoView> getMachineriesByWorkId(Long workId) {
        List<Machinery> machineries = machineryRepository.findMachineryByWorkId(workId);

        return machineries.stream()
                .map(MachineryMapper::toBasicInfoView)
                .toList();
    }

    @Override
    @Transactional
    public void changeMachineryStatusByWorkIds(Set<Long> workIds, StatusCode statusCode, LocalDateTime changedAt) {
        if (workIds == null || workIds.isEmpty()) {
            throw new EmptyCollectionException();
        }

        List<Machinery> machineries = machineryRepository.findAllByWorkIds(workIds);
        List<StatusCode> allowedStatuses = List.of(StatusCode.IDLE, StatusCode.IN_OPERATION);

        List<Machinery> conflictingMachineries = machineries.stream()
                .filter(machinery -> !allowedStatuses.contains(machinery.getCurrentStatus()))
                .toList();

        if (!conflictingMachineries.isEmpty()) {
            List<Long> machineryIds = conflictingMachineries.stream().map(Machinery::getId).toList();
            List<Integer> inventoryNumbers = conflictingMachineries.stream().map(Machinery::getInventoryNumber).toList();
            throw new MachineryStatusConflictException(machineryIds, inventoryNumbers, statusCode);
        }

        machineries.forEach(machinery -> machinery.setCurrentStatus(statusCode));
        machineryRepository.saveAll(machineries);
        machineryStatusHistoryService.createAllHistoryRecords(machineries, changedAt);
    }

    @Override
    @Transactional
    public void updateMachinery(Long id, MachineryUpdateForm machineryUpdateForm) {
        Machinery machinery = machineryRepository.findById(id).orElseThrow(() -> new MachineryNotFoundException(Set.of(id)));

        if (machinery.getCurrentStatus() == StatusCode.DECOMMISSIONED) {
            throw new MachineryCannotBeModifiedException(machinery.getId());
        }

        StatusCode newStatusCode = machineryUpdateForm.getStatus();

        machinery.setMachineryName(machineryUpdateForm.getMachineryName());
        machinery.setMachineryType(machineryUpdateForm.getMachineryType());
        machinery.setLicensePlate(machineryUpdateForm.getLicensePlate());
        machinery.setInventoryNumber(machineryUpdateForm.getInventoryNumber());
        machinery.setPurchaseDate(machineryUpdateForm.getPurchaseDate());

        if (newStatusCode != machinery.getCurrentStatus()) {
            machinery.setCurrentStatus(newStatusCode);
            machineryStatusHistoryService.createHistoryRecord(machinery, LocalDateTime.now());
        }

        machineryRepository.save(machinery);
    }

//    private Map<Long, StatusCode> getMachineryStatusesByIds(Set<Long> machineryIds) {
//        List<Machinery> machineryStatusViews = machineryRepository.findMachineryByIdIn(machineryIds);
//
//        return machineryStatusViews.stream()
//                .collect(Collectors.toMap(
//                        Machinery::getId,
//                        Machinery::getCurrentStatus
//                ));
//    }


}
