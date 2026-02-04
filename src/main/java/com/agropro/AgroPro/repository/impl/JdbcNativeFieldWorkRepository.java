package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.FieldWork;
import com.agropro.AgroPro.repository.FieldWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class JdbcNativeFieldWorkRepository implements FieldWorkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(FieldWork fieldWork) {
        String query = "INSERT INTO field_works(work_type_id, field_id, start_date, end_date, description) " +
                "VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, fieldWork.getWorkTypeId());
            preparedStatement.setLong(2, fieldWork.getFieldId());
            preparedStatement.setObject(3, fieldWork.getStartDate());
            preparedStatement.setObject(4, fieldWork.getEndDate());
            preparedStatement.setString(5, fieldWork.getDescription());

            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("field_work_id")).longValue();
        fieldWork.setFieldWorkId(generatedId);

        return generatedId;
    }

    @Override
    public void linkEmployees(Long workId, Set<Long> employeeIds) {
        String query = "INSERT INTO field_work_employees(field_work_id, employee_id) VALUES (?, ?)";

        List<Object[]> arguments = employeeIds.stream().map(employeeId -> new Object[]{workId, employeeId}).toList();

        jdbcTemplate.batchUpdate(query, arguments);
    }

    @Override
    public void linkMachineries(Long workId, Set<Long> machineryIds) {
        String query = "INSERT INTO field_work_machineries(field_work_id, machinery_id) VALUES (?, ?)";

        List<Object[]> arguments = machineryIds.stream().map(machineryId -> new Object[]{workId, machineryId}).toList();

        jdbcTemplate.batchUpdate(query, arguments);
    }

    @Override
    public void linkEquipment(Long workId, Set<Long> equipmentIds) {
        String query = "INSERT INTO field_work_equipment(field_work_id, equipment_id) VALUES (?, ?)";

        List<Object[]> arguments = equipmentIds.stream()
                .map(equipmentId -> new Object[]{workId, equipmentId})
                .toList();

        jdbcTemplate.batchUpdate(query, arguments);
    }
}
