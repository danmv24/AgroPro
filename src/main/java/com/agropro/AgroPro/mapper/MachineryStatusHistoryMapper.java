package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.model.MachineryStatusHistory;

import java.time.LocalDateTime;

public class MachineryStatusHistoryMapper {

    public static MachineryStatusHistory toModel(Machinery machinery, LocalDateTime now) {
        return MachineryStatusHistory.builder()
                .machineryId(machinery.getId())
                .status(machinery.getCurrentStatus())
                .changedAt(LocalDateTime.now())
                .build();
    }

}
