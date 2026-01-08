package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.repository.MachineryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcNativeMachineryRepository implements MachineryRepository {

    private final JdbcTemplate jdbcTemplate;

}
