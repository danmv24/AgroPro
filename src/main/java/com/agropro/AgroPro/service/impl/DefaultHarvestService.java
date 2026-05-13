package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.HarvestValidationException;
import com.agropro.AgroPro.mapper.HarvestMapper;
import com.agropro.AgroPro.repository.HarvestRepository;
import com.agropro.AgroPro.service.HarvestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DefaultHarvestService implements HarvestService {

    private final HarvestRepository harvestRepository;


    @Override
    public void createHarvestRecord(Long workId, BigDecimal grossHarvest) {
        if (grossHarvest == null || grossHarvest.compareTo(BigDecimal.ZERO) <= 0) {
            throw new HarvestValidationException("Валовый сбор должен быть больше нуля");
        }

        LocalDateTime now = LocalDateTime.now();
        harvestRepository.save(HarvestMapper.toModel(workId, grossHarvest, now));
    }


}
