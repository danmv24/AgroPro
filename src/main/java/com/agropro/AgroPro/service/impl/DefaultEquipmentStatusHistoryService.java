package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.repository.EquipmentStatusHistoryRepository;
import com.agropro.AgroPro.service.EquipmentStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentStatusHistoryService implements EquipmentStatusHistoryService {

    private final EquipmentStatusHistoryRepository equipmentStatusHistoryRepository;

    @Override
    public void addHistoryRecord(Long equipmentId, Long statusId) {
        equipmentStatusHistoryRepository.save(equipmentId, statusId);
    }
}
