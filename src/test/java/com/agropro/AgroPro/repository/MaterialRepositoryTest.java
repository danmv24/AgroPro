package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.enums.MaterialType;
import com.agropro.AgroPro.model.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class MaterialRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private MaterialRepository materialRepository;


    @BeforeEach
    void setUp() {
        materialRepository.saveAll(List.of(
                Material.builder()
                        .materialName("Семена пшеницы №1")
                        .materialType(MaterialType.SEEDS)
                        .currentPrice(new BigDecimal(300))
                        .build(),
                Material.builder()
                        .materialName("Бензин 95")
                        .materialType(MaterialType.FUEL)
                        .currentPrice(new BigDecimal(100))
                        .build()
        ));
    }

    @Test
    void findAll_ShouldReturnMaterialsWithPagination() {
        Slice<Material> materials = materialRepository.findAll(PageRequest.of(0, 2));

        assertThat(materials.getContent()).hasSize(2);
        assertThat(materials.hasNext()).isFalse();
    }

}
