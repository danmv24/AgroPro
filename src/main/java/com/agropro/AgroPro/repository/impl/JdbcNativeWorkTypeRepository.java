package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.WorkType;
import com.agropro.AgroPro.repository.WorkTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeWorkTypeRepository implements WorkTypeRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<WorkType> findAll() {
        String query = "SELECT work_type_id, work_name FROM work_types;";

        return jdbcTemplate.query(query, (rs, rowNum) -> WorkType.builder()
                .workTypeId(rs.getLong("work_type_id"))
                .workName(rs.getString("work_name"))
                .build());
    }

    @Override
    public boolean existWorkTypeById(Long workTypeId) {
        String query = "SELECT EXISTS(SELECT 1 FROM work_types WHERE work_type_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, workTypeId));
    }


}