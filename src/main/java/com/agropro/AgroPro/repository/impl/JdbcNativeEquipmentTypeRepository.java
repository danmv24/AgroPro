package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.EquipmentType;
import com.agropro.AgroPro.repository.EquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEquipmentTypeRepository implements EquipmentTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<EquipmentType> findAll() {
        String query = "SELECT id, equipment_type FROM equipment_types ORDER BY equipment_types";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    EquipmentType.builder()
                            .id(rs.getLong("id"))
                            .equipmentType(rs.getString("equipment_type"))
                            .build()
                );
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT EXISTS(SELECT 1 FROM equipment_types WHERE id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, id));
    }


}
