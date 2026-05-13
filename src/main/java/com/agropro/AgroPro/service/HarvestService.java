package com.agropro.AgroPro.service;

import java.math.BigDecimal;

public interface HarvestService {

    void createHarvestRecord(Long workId, BigDecimal grossHarvest);

}
