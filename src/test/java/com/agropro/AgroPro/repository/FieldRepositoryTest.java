package com.agropro.AgroPro.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest

public class FieldRepositoryTest extends RepositoryIntegrationTest {

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    void sumAllFieldsArea_shouldReturnTotalArea() {
        BigDecimal totalArea = fieldRepository.sumAllFieldsArea();

        assertThat(totalArea).isEqualByComparingTo("2258");
    }

}
