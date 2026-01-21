package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.view.EquipmentView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEquipmentRepository implements EquipmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Equipment equipment) {
        String query = "INSERT INTO equipment(equipment_name, equipment_type_id, inventory_number, current_status_id, purchase_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, equipment.getEquipmentName());
            preparedStatement.setLong(2, equipment.getEquipmentTypeId());
            preparedStatement.setInt(3, equipment.getInventoryNumber());
            preparedStatement.setLong(4, equipment.getCurrentStatusId());
            preparedStatement.setObject(5, equipment.getPurchaseDate());

            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("equipment_id")).longValue();
        equipment.setEquipmentId(generatedId);
    }

    @Override
    public List<EquipmentView> findAll() {
        String query = "SELECT e.equipment_name, e.inventory_number, et.equipment_type, sc.display_name " +
                "FROM equipment AS e " +
                "INNER JOIN equipment_types AS et ON e.equipment_type_id = et.id " +
                "INNER JOIN status_codes AS sc ON e.status_id = sc.status_id " +
                "ORDER BY e.equipment_name";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    EquipmentView.builder()
                            .equipmentName(rs.getString("equipment_name"))
                            .equipmentType(rs.getString("equipment_type"))
                            .inventoryNumber(rs.getInt("inventory_number"))
                            .statusCode(rs.getString("display_name"))
                            .build()
                );
    }


}
