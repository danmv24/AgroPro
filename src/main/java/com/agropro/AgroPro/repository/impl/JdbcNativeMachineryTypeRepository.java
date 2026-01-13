package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.MachineryType;
import com.agropro.AgroPro.repository.MachineryTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeMachineryTypeRepository implements MachineryTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MachineryType> findAll() {
        String query = "SELECT id, machinery_type FROM machinery_types";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                MachineryType.builder()
                        .id(rs.getLong("id"))
                        .machineryType(rs.getString("machinery_type"))
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        String query = "SELECT EXISTS(SELECT 1 FROM machinery_types WHERE id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, id));
    }


}
