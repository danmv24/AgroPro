package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.enums.MaterialType;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import com.agropro.AgroPro.model.*;
import com.agropro.AgroPro.projection.CropMaterialCost;
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

public class WorkMaterialUsageRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private WorkMaterialUsageRepository workMaterialUsageRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private FieldPlantingRepository fieldPlantingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @BeforeEach
    void setUp() {
        Field f1 = fieldRepository.findById(1L).orElseThrow();
        Field f2 = fieldRepository.findById(2L).orElseThrow();
        Field f3 = fieldRepository.findById(3L).orElseThrow();

        Material fuel = materialRepository.save(Material.builder()
                .materialName("Бензин 95")
                .materialType(MaterialType.FUEL)
                .currentPrice(new BigDecimal(60))
                .build());

        Material seeds = materialRepository.save(Material.builder()
                .materialName("Семена пшеницы")
                .materialType(MaterialType.SEEDS)
                .currentPrice(new BigDecimal(450))
                .build());

        Material fertilizer = materialRepository.save(Material.builder()
                .materialName("Удобрение для земли")
                .materialType(MaterialType.FERTILIZER)
                .currentPrice(new BigDecimal(1200))
                .build());

        fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f1.getId())
                .cropType(CropType.WINTER_WHEAT)
                .plantingDate(LocalDate.of(2025, 10, 1))
                .harvestDate(null).build());

        fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f2.getId()).cropType(CropType.SPRING_WHEAT)
                .plantingDate(LocalDate.of(2026, 4, 15))
                .harvestDate(LocalDate.of(2026, 7, 10))
                .build());

        fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f3.getId())
                .cropType(CropType.SUNFLOWER)
                .plantingDate(LocalDate.of(2026, 3, 1))
                .harvestDate(LocalDate.of(2026, 5, 15))
                .build());

        Work w1 = workRepository.save(Work.builder()
                .workType(WorkType.SOWING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 10, 8, 0))
                .endDate(LocalDateTime.of(2026, 6, 10, 16, 0))
                .build());

        Work w2 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f2.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 15, 9, 0))
                .endDate(LocalDateTime.of(2026, 6, 15, 17, 0))
                .build());

        Work w3 = workRepository.save(Work.builder()
                .workType(WorkType.FERTILIZING)
                .fieldId(f1.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 5, 20, 8, 0))
                .endDate(LocalDateTime.of(2026, 5, 20, 16, 0))
                .build());

        Work w4 = workRepository.save(Work.builder()
                .workType(WorkType.HARVESTING)
                .fieldId(f3.getId())
                .status(WorkStatus.COMPLETED)
                .startDate(LocalDateTime.of(2026, 6, 20, 10, 0))
                .endDate(LocalDateTime.of(2026, 6, 20, 18, 0))
                .build());

        workMaterialUsageRepository.saveAll(List.of(
                WorkMaterialUsage.builder()
                        .workId(w1.getId())
                        .materialId(fuel.getId())
                        .quantity(new BigDecimal(10))
                        .pricePerUnit(new BigDecimal(60))
                        .totalCost(new BigDecimal(600))
                        .build(),
                WorkMaterialUsage.builder()
                        .workId(w1.getId())
                        .materialId(seeds.getId())
                        .quantity(new BigDecimal(2))
                        .pricePerUnit(new BigDecimal(450))
                        .totalCost(new BigDecimal(900))
                        .build(),
                WorkMaterialUsage.builder()
                        .workId(w2.getId())
                        .materialId(fertilizer.getId())
                        .quantity(new BigDecimal("1.5"))
                        .pricePerUnit(new BigDecimal(1200))
                        .totalCost(new BigDecimal(1800))
                        .build(),
                WorkMaterialUsage.builder()
                        .workId(w3.getId())
                        .materialId(fuel.getId())
                        .quantity(new BigDecimal(20))
                        .pricePerUnit(new BigDecimal(60))
                        .totalCost(new BigDecimal(1200))
                        .build(),
                WorkMaterialUsage.builder()
                        .workId(w4.getId())
                        .materialId(fuel.getId())
                        .quantity(new BigDecimal(5))
                        .pricePerUnit(new BigDecimal(60))
                        .totalCost(new BigDecimal(300))
                        .build()
        ));


    }

    @Test
    void findCostsByCropTypeAndMaterialTypeBetweenDateRange_shouldReturnCosts() {
        List<CropMaterialCost> result = workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(
                LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 30));

        assertThat(result).hasSize(3);

        CropMaterialCost winterWheatFuel = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT && r.getMaterialType() == MaterialType.FUEL)
                .findFirst()
                .orElseThrow();

        CropMaterialCost winterWheatSeeds = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT && r.getMaterialType() == MaterialType.SEEDS)
                .findFirst()
                .orElseThrow();

        CropMaterialCost springWheatFertilizer = result.stream()
                .filter(r -> r.getCropType() == CropType.SPRING_WHEAT && r.getMaterialType() == MaterialType.FERTILIZER)
                .findFirst()
                .orElseThrow();

        assertThat(winterWheatFuel.getTotalCost()).isEqualByComparingTo(new BigDecimal(600));
        assertThat(winterWheatSeeds.getTotalCost()).isEqualByComparingTo(new BigDecimal(900));
        assertThat(springWheatFertilizer.getTotalCost()).isEqualByComparingTo(new BigDecimal(1800));
    }

    @Test
    void findCostsByCropTypeAndMaterialTypeBetweenDateRange_shouldReturnEmpty() {
        LocalDate start = LocalDate.of(2027, 1, 1);
        LocalDate end   = LocalDate.of(2027, 12, 31);

        assertThat(workMaterialUsageRepository.findCostsByCropTypeAndMaterialTypeBetweenDateRange(start, end)).isEmpty();
    }


}
