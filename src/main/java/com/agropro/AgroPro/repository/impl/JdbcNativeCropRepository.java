package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.view.CropView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeCropRepository implements CropRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<CropView> findAll() {
        String query = "SELECT crop_id, crop_name FROM crops";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    CropView.builder()
                            .cropId(rs.getLong("crop_id"))
                            .cropName(rs.getString("crop_name"))
                            .build()
                );
    }

    @Override
    public boolean existsByCropId(Long cropId) {
        String query = "SELECT EXISTS(SELECT 1 FROM crops WHERE crop_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, cropId));
    }
}
