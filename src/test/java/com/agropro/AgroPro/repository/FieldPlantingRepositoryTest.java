package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.CropType;
import com.agropro.AgroPro.model.Field;
import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.projection.CropArea;
import com.agropro.AgroPro.projection.CropSownArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class FieldPlantingRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private FieldPlantingRepository fieldPlantingRepository;

    @Autowired
    private FieldRepository fieldRepository;

    private FieldPlanting fp1;

    private FieldPlanting fp2;

    private FieldPlanting fp3;

    private Field f1;

    private Field f2;

    private Field f3;

    @BeforeEach
    void setUp() {
        f1 = fieldRepository.findById(1L).orElseThrow();
        f2 = fieldRepository.findById(2L).orElseThrow();
        f3 = fieldRepository.findById(3L).orElseThrow();

        fp1 = fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f1.getId())
                .cropType(CropType.WINTER_WHEAT)
                .plantingDate(LocalDate.of(2026, 3, 1))
                .harvestDate(null)
                .build());

        fp2 = fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f2.getId())
                .cropType(CropType.SUNFLOWER)
                .plantingDate(LocalDate.of(2026, 3, 15))
                .harvestDate(LocalDate.of(2026, 5, 1))
                .build());

        fp3 = fieldPlantingRepository.save(FieldPlanting.builder()
                .fieldId(f3.getId())
                .cropType(CropType.WINTER_WHEAT)
                .plantingDate(LocalDate.of(2026, 4, 1))
                .harvestDate(LocalDate.of(2026, 8, 15))
                .build());
    }

    @Test
    void findFieldPlantingByFieldId_shouldReturnActivePlanting() {
        Optional<FieldPlanting> result = fieldPlantingRepository.findFieldPlantingByFieldId(f1.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getCropType()).isEqualTo(CropType.WINTER_WHEAT);
        assertThat(result.get().getHarvestDate()).isNull();
    }

    @Test
    void findFieldPlantingByFieldId_shouldReturnEmptyWhenNoActivePlanting() {
        Optional<FieldPlanting> result = fieldPlantingRepository.findFieldPlantingByFieldId(f2.getId());

        assertThat(result).isEmpty();
    }

    @Test
    void findAllByFieldIdsAndDate_shouldReturnPlantingsActiveAtDate() {
        List<FieldPlanting> result = fieldPlantingRepository.findAllByFieldIdsAndDate(Set.of(f1.getId(), f2.getId(), f3.getId()),
                LocalDate.of(2026, 5, 1));

        assertThat(result).hasSize(2)
                .extracting(FieldPlanting::getFieldId)
                .containsExactlyInAnyOrder(f1.getId(), f3.getId());
    }

    @Test
    void findAllByFieldIdsAndDate_shouldIgnoreHarvestedPlantings() {
        List<FieldPlanting> result = fieldPlantingRepository.findAllByFieldIdsAndDate(Set.of(f1.getId(), f2.getId(), f3.getId()),
                        LocalDate.of(2026, 10, 1));

        assertThat(result).hasSize(1)
                .extracting(FieldPlanting::getId)
                .containsExactly(fp1.getId());
    }

    @Test
    void findSownAndHarvestedAreas_shouldReturnAggregatedAreas() {
        List<CropArea> result = fieldPlantingRepository.findSownAndHarvestedAreas(LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31));

        assertThat(result).hasSize(2);

        CropArea winterWheat = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT)
                .findFirst()
                .orElseThrow();

        CropArea sunflower = result.stream()
                .filter(r -> r.getCropType() == CropType.SUNFLOWER)
                .findFirst()
                .orElseThrow();

        assertThat(winterWheat.getSownArea()).isEqualByComparingTo(new BigDecimal("570"));
        assertThat(winterWheat.getHarvestedArea()).isEqualByComparingTo(new BigDecimal("185"));

        assertThat(sunflower.getSownArea()).isEqualByComparingTo(new BigDecimal("345"));
        assertThat(sunflower.getHarvestedArea()).isEqualByComparingTo(new BigDecimal("345"));
    }

    @Test
    void findSownArea_shouldReturnOnlySownAreas() {
        List<CropSownArea> result = fieldPlantingRepository.findSownArea(LocalDate.of(2026, 1, 1),
                        LocalDate.of(2026, 12, 31));

        assertThat(result).hasSize(2);

        CropSownArea wheat = result.stream()
                .filter(r -> r.getCropType() == CropType.WINTER_WHEAT)
                .findFirst()
                .orElseThrow();

        CropSownArea sunflower = result.stream()
                .filter(r -> r.getCropType() == CropType.SUNFLOWER)
                .findFirst()
                .orElseThrow();

        assertThat(wheat.getSownArea()).isEqualByComparingTo(new BigDecimal("570"));

        assertThat(sunflower.getSownArea()).isEqualByComparingTo(new BigDecimal("345"));
    }


}
