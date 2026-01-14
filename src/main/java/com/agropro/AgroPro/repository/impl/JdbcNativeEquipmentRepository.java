package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEquipmentRepository implements EquipmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Equipment equipment) {
        String query = "INSERT INTO equipment(equipment_name, equipment_type_id, inventory_number, status_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, equipment.getEquipmentName());
            preparedStatement.setLong(2, equipment.getEquipmentTypeId());
            preparedStatement.setInt(3, equipment.getInventoryNumber());
            preparedStatement.setLong(4, equipment.getStatusId());

            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("equipment_id")).longValue();
        equipment.setEquipmentId(generatedId);
    }



}
