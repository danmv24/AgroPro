package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.MachineryNotFoundException;
import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.mapper.MachineryMapper;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.service.MachineryService;
import com.agropro.AgroPro.service.MachineryStatusHistoryService;
import com.agropro.AgroPro.service.MachineryTypeService;
import com.agropro.AgroPro.service.StatusService;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultMachineryService implements MachineryService {

    private final MachineryRepository machineryRepository;
    private final MachineryTypeService machineryTypeService;
    private final StatusService statusService;
    private final MachineryStatusHistoryService machineryStatusHistoryService;

    @Transactional
    @Override
    public void addMachinery(MachineryForm machineryForm) {
        machineryTypeService.validateMachineryTypeExistsById(machineryForm.getMachineryTypeId());
        Long idleStatusId = statusService.getIdleStatusCodeId();

        Long machineryId = machineryRepository.save(MachineryMapper.toModel(machineryForm, idleStatusId));

        machineryStatusHistoryService.addHistoryRecord(machineryId, idleStatusId);
    }

    @Override
    public List<MachineryView> getMachineries() {
        return machineryRepository.findAll();
    }

    @Override
    public List<MachineryBasicInfoView> getIdleMachineries() {
        return machineryRepository.findMachineriesWithIdleStatus();
    }

    @Override
    public void validateMachineriesExistByIds(Set<Long> machineryIds) {
        if (machineryIds == null || machineryIds.isEmpty()) {
            throw new IllegalArgumentException("Отправлен пустой список техники для проверки на существование");
        }

        Set<Long> existingIds = machineryRepository.findExistingMachineriesByIds(machineryIds);

        if (existingIds.size() != machineryIds.size()) {
            Set<Long> missingIds = new HashSet<>(machineryIds);
            missingIds.removeAll(existingIds);
            throw new MachineryNotFoundException(missingIds);
        }
    }

    @Override
    public Map<Long, Long> getMachineryStatusesByIds(Set<Long> machineryIds) {
        return machineryRepository.findMachineryStatusesByIds(machineryIds);
    }

    @Override
    public void validateMachineryStatus(Set<Long> machineryIds) {
        if (machineryIds == null || machineryIds.isEmpty()) {
            return;
        }

        Long idleStatusId = statusService.getIdleStatusCodeId();
        Map<Long, Long> statusesById = getMachineryStatusesByIds(machineryIds);

        for (Long machineryId : machineryIds) {
            Long machineryStatusId = statusesById.get(machineryId);
            if (machineryStatusId == null) {
                throw new RuntimeException("Пока что заглушка 4");
            }
            if (!machineryStatusId.equals(idleStatusId)) {
                throw new RuntimeException("Пока что заглушка 5");
            }
        }
    }

    @Override
    public void validateMachineriesAvailability(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        if (machineryIds == null || machineryIds.isEmpty()) {
            return;
        }

        List<Long> conflictMachineryIds = machineryRepository.findConflictMachineryIdsByDateTime(machineryIds, startDateOfWork, endDateOfWork);

        if (!conflictMachineryIds.isEmpty()) {
            throw new RuntimeException("Пока что заглушка 6");
        }
    }

    @Override
    public List<MachineryBasicInfoView> getMachineriesByFieldWorkId(Long workId) {
        return machineryRepository.findMachineriesByFieldWorkId(workId);
    }


}
