package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@RequiredArgsConstructor
@Repository
public class JdbcNativeFieldPlantingRepository implements FieldPlantingRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(FieldPlanting fieldPlanting) {
        String query = "INSERT INTO field_plantings (field_id, crop_id, planting_year) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, fieldPlanting.getFieldId());
            preparedStatement.setLong(2, fieldPlanting.getCropId());
            preparedStatement.setInt(3, fieldPlanting.getPlantingYear());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("id")).longValue();
        fieldPlanting.setId(generatedId);
    }
}
