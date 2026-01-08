package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEquipmentRepository implements EquipmentRepository {

    private final JdbcTemplate jdbcTemplate;

}
