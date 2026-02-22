package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.mapper.EquipmentStatusHistoryMapper;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.model.EquipmentStatusHistory;
import com.agropro.AgroPro.repository.EquipmentStatusHistoryRepository;
import com.agropro.AgroPro.service.EquipmentStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultEquipmentStatusHistoryService implements EquipmentStatusHistoryService {

    private final EquipmentStatusHistoryRepository equipmentStatusHistoryRepository;

    @Override
    public void createHistoryRecord(Equipment equipment, LocalDateTime changedAt) {
        equipmentStatusHistoryRepository.save(EquipmentStatusHistoryMapper.toModel(equipment, changedAt));
    }

    @Override
    public void createAllHistoryRecords(List<Equipment> equipment, LocalDateTime changedAt) {
        List<EquipmentStatusHistory> historyRecords = equipment.stream().map(e ->
                EquipmentStatusHistoryMapper.toModel(e, changedAt))
                .toList();

        equipmentStatusHistoryRepository.saveAll(historyRecords);
    }


}
