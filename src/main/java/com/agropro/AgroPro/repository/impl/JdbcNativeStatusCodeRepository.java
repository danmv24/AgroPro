package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.StatusCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcNativeStatusCodeRepository implements StatusCodeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Long> findStatusIdByStatusCode(String statusCode) {
        String query = "SELECT status_id FROM status_codes WHERE status_code = ?";
        Long statusId = jdbcTemplate.queryForObject(query,  Long.class, statusCode);

        return Optional.ofNullable(statusId);
    }
}
