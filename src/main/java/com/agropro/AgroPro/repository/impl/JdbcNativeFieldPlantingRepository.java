package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.FieldPlanting;
import com.agropro.AgroPro.repository.FieldPlantingRepository;
import com.agropro.AgroPro.view.FieldPlantingView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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

    @Override
    public List<FieldPlantingView> findPlantingsByFieldId(Long fieldId) {
        String query = "SELECT c.crop_name, fp.planting_year FROM field_plantings AS fp " +
                "INNER JOIN crops AS c ON fp.crop_id = c.crop_id " +
                "WHERE fp.field_id = ? " +
                "ORDER BY fp.planting_year DESC";

        return jdbcTemplate.query(query, (rs, rowNum) -> FieldPlantingView.builder()
                    .plantingYear(rs.getInt("planting_year"))
                    .cropName(rs.getString("crop_name"))
                    .build(), fieldId);
    }
}
