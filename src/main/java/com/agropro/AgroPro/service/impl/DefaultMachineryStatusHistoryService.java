package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.mapper.MachineryStatusHistoryMapper;
import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.model.MachineryStatusHistory;
import com.agropro.AgroPro.repository.MachineryStatusHistoryRepository;
import com.agropro.AgroPro.service.MachineryStatusHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class DefaultMachineryStatusHistoryService implements MachineryStatusHistoryService {

    private final MachineryStatusHistoryRepository machineryStatusHistoryRepository;

    @Override
    public void createHistoryRecord(Machinery machinery, LocalDateTime changedAt) {
        machineryStatusHistoryRepository.save(MachineryStatusHistoryMapper.toModel(machinery, changedAt));
    }


    @Override
    public void createAllHistoryRecords(List<Machinery> machineries, LocalDateTime changedAt) {
        List<MachineryStatusHistory> historyRecords = machineries.stream().map(machinery ->
            MachineryStatusHistoryMapper.toModel(machinery, changedAt)
        ).toList();

        machineryStatusHistoryRepository.saveAll(historyRecords);
    }
}
