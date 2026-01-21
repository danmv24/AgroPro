package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.EquipmentStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEquipmentStatusHistoryRepository implements EquipmentStatusHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Long equipmentId, Long statusId) {
        String query = "INSERT INTO equipment_status_history(machinery_id, status_id, changed_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(query, equipmentId, statusId);
    }
}
