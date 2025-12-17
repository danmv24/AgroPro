package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.view.FieldView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeFieldRepository implements FieldRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<FieldView> findAll() {
        String query = "SELECT field_id, field_number FROM fields";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    FieldView.builder()
                            .fieldId(rs.getLong("field_id"))
                            .fieldNumber(rs.getInt("field_number"))
                            .build()
                );
    }

    @Override
    public boolean existsByFieldId(Long fieldId) {
        String query = "SELECT EXISTS(SELECT 1 FROM fields WHERE field_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, fieldId));
    }


}
