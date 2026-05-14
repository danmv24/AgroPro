package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.response.HarvestResponse;

import java.math.BigDecimal;

public interface HarvestService {

    void createHarvestRecord(Long workId, BigDecimal grossHarvest);

    HarvestResponse getHarvestByWorkId(Long workId);
}
