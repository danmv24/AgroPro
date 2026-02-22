package com.agropro.AgroPro.service;

import com.agropro.AgroPro.model.Machinery;

import java.time.LocalDateTime;
import java.util.List;

public interface MachineryStatusHistoryService {

    void createHistoryRecord(Machinery machinery, LocalDateTime changedAt);

    void createAllHistoryRecords(List<Machinery> machineries, LocalDateTime changedAt);
}
