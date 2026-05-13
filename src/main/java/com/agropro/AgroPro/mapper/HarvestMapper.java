package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.Harvest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HarvestMapper {

    public static Harvest toModel(Long workId, BigDecimal grossHarvest, LocalDateTime now) {
        return Harvest.builder()
                .workId(workId)
                .grossHarvest(grossHarvest)
                .createdAt(now)
                .build();
    }

}
