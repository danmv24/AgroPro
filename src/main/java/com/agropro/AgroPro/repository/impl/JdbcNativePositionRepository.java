package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcNativePositionRepository implements PositionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Position> findAll() {
        String query = "SELECT position_id, position_name FROM positions";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    Position.builder()
                            .positionId(rs.getLong("position_id"))
                            .positionName(rs.getString("position_name"))
                            .build()
                );
    }

    @Override
    public Optional<Long> findPositionIdByPositionName(String positionName) {
        String query = "SELECT position_id FROM positions WHERE position_name = ?";
        Long positionId = jdbcTemplate.queryForObject(query, Long.class, positionName);

        return Optional.ofNullable(positionId);
    }

    @Override
    public Optional<String> findPositionNameByPositionId(Long positionId) {
        String query = "SELECT position_name FROM positions WHERE position_id = ?";
        String positionName = jdbcTemplate.queryForObject(query, String.class, positionId);

        return Optional.ofNullable(positionName);
    }


}
