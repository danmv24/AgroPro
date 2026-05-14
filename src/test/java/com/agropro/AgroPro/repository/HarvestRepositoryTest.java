package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.model.Harvest;
import com.agropro.AgroPro.model.Work;
import com.agropro.AgroPro.projection.CropHarvest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class HarvestRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private FieldPlantingRepository fieldPlantingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    private FieldPlanting winterWheatPlanting;

    private FieldPlanting springWheatPlanting;

    private Field f1;

    private Field f2;

    @BeforeEach
    void test() {
        f1 = fieldRepository.findById(1L).orElseThrow();
        f2 = fieldRepository.findById(2L).orElseThrow();

        winterWheatPlanting = fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(1L)
                .cropType(CropType.WINTER_WHEAT)
                .plantingDate(LocalDate.of(2025, 10, 1))
                .build());

        springWheatPlanting = fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(2L)
                .cropType(CropType.SPRING_WHEAT)
                .plantingDate(LocalDate.of(2026, 4, 15))
                .build());

        Work w1 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 5, 8, 0))
                .endDate(LocalDateTime.of(2026, 6, 5, 16, 0))
                .build());

        Work w2 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 10, 9, 0))
                .endDate(LocalDateTime.of(2026, 6, 10, 17, 0))
                .build());

        Work w3 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 20, 8, 0))
                .endDate(LocalDateTime.of(2026, 5, 20, 16, 0))
                .build());

        Work w4 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f2.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 20, 10, 0))
                .endDate(LocalDateTime.of(2026, 6, 20, 18, 0))
                .build());

        harvestRepository.saveAll(List.of(
                Harvest.builder()
                        .workId(w1.getId())
                        .grossHarvest(new BigDecimal(150))
                        .build(),
                Harvest.builder()
                        .workId(w2.getId())
                        .grossHarvest(new BigDecimal(200))
                        .build(),
                Harvest.builder()
                        .workId(w3.getId())
                        .grossHarvest(new BigDecimal(999))
                        .build(),
                Harvest.builder().
                        workId(w4.getId())
                        .grossHarvest(new BigDecimal(300))
                        .build()
        ));
    }

    @Test
    void findTotalHarvestByCropTypeAndDateRange_shouldReturnSumOfHarvest() {
        List<CropHarvest> result = harvestRepository.findTotalHarvestByCropTypeAndDateRange(LocalDate.of(2026, 6, 1),
                LocalDate.of(2026, 6, 30));
        assertThat(result).hasSize(2);

        CropHarvest winterWheat = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT)
                .findFirst()
                .orElseThrow();

        CropHarvest springWheat = result.stream()
                .filter(r -> r.getCropType() == CropType.SPRING_WHEAT)
                .findFirst()
                .orElseThrow();

        assertThat(winterWheat.getTotalGrossHarvest()).isEqualByComparingTo(new BigDecimal("350"));
        assertThat(springWheat.getTotalGrossHarvest()).isEqualByComparingTo(new BigDecimal("300"));
    }

    @Test
    void findTotalHarvestByCropTypeAndDateRange_shouldReturnEmptyForNonOverlappingRange() {
        List<CropHarvest> result = harvestRepository.findTotalHarvestByCropTypeAndDateRange(LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31));

        assertThat(result).isEmpty();
    }

}
