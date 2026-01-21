package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.repository.MachineryStatusHistoryRepository;
import com.agropro.AgroPro.service.MachineryStatusHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultMachineryStatusHistoryService implements MachineryStatusHistoryService {

    private final MachineryStatusHistoryRepository machineryStatusHistoryRepository;

    @Override
    public void addHistoryRecord(Long machineryId, Long statusId) {
        machineryStatusHistoryRepository.save(machineryId, statusId);
    }
}
