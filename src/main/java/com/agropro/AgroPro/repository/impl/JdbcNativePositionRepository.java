package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Position;
import com.agropro.AgroPro.repository.PositionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativePositionRepository implements PositionRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativePositionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Position> findByPositionName(String positionName) {
        String query = "SELECT position_id, position_name FROM positions WHERE position_name = ?";

        Position position = jdbcTemplate.queryForObject(query, (rs, rowNum) ->
                        Position.builder()
                        .positionId(rs.getLong("position_id"))
                        .positionName(rs.getString("position_name"))
                        .build()
                ,positionName);

        return Optional.ofNullable(position);
    }

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
    public Optional<Position> findByPositionId(Long positionId) {
        String query = "SELECT position_id, position_name FROM positions WHERE position_id = ?";

        Position position = jdbcTemplate.queryForObject(query, ((rs, rowNum) ->
                    Position.builder()
                            .positionId(rs.getLong("position_id"))
                            .positionName(rs.getString("position_name"))
                            .build()
                ), positionId);

        return Optional.ofNullable(position);
    }


}
