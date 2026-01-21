package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.MachineryStatusHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcNativeMachineryStatusHistoryRepository implements MachineryStatusHistoryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Long machineryId, Long statusId) {
        String query = "INSERT INTO machinery_status_history(machinery_id, status_id, changed_at) VALUES (?, ?, NOW())";
        jdbcTemplate.update(query, machineryId, statusId);
    }
}
