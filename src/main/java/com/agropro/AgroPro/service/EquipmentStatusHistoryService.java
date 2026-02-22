package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.Equipment;

import java.time.LocalDateTime;
import java.util.List;

public interface EquipmentStatusHistoryService {

    void createHistoryRecord(Equipment equipment, LocalDateTime changedAt);

    void createAllHistoryRecords(List<Equipment> equipment, LocalDateTime changedAt);

}
