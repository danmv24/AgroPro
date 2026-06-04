package com.agropro.AgroPro.service;

import com.agropro.AgroPro.enums.EquipmentType;
import com.agropro.AgroPro.enums.StatusCode;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.model.EquipmentStatusHistory;
import com.agropro.AgroPro.repository.EquipmentStatusHistoryRepository;
import com.agropro.AgroPro.service.impl.DefaultEquipmentStatusHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultEquipmentStatusHistoryTest {

    @Mock
    private EquipmentStatusHistoryRepository historyRepository;

    @InjectMocks
    private DefaultEquipmentStatusHistoryService historyService;

    @Test
    void createHistoryRecord_shouldSave() {
        Equipment equipment = new Equipment(1L, "Дискатор", EquipmentType.DISKATOR, 1, StatusCode.IDLE, LocalDate.now());
        LocalDateTime changedAt = LocalDateTime.now();

        historyService.createHistoryRecord(equipment, changedAt);

        verify(historyRepository).save(any(EquipmentStatusHistory.class));
    }

    @Test
    void createAllHistoryRecords_shouldSaveAllHistoryRecords() {
        List<Equipment> equipmentList = List.of(new Equipment(1L, "Дискатор", EquipmentType.DISKATOR, 1, StatusCode.IDLE, LocalDate.now()),
                                                new Equipment(1L, "Культиватор", EquipmentType.CULTIVATOR, 1, StatusCode.IDLE, LocalDate.now()));
        LocalDateTime changedAt = LocalDateTime.now();

        historyService.createAllHistoryRecords(equipmentList, changedAt);

        verify(historyRepository).saveAll(anyList());
    }

}
