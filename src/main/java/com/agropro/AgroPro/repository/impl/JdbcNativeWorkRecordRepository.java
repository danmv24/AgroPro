package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.WorkRecord;
import com.agropro.AgroPro.repository.WorkRecordRepository;
import com.agropro.AgroPro.view.WorkRecordView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class JdbcNativeWorkRecordRepository implements WorkRecordRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativeWorkRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(WorkRecord workRecord) {
        String query = "INSERT INTO work_records (employee_id, work_date, hours_worked) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setLong(1, workRecord.getEmployeeId());
            preparedStatement.setDate(2, Date.valueOf(workRecord.getWorkDate()));
            preparedStatement.setBigDecimal(3, workRecord.getHoursWorked());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("id")).longValue();
        workRecord.setId(generatedId);
    }

    @Override
    public List<WorkRecordView> findAllWorkRecords() {
        String query = "SELECT e.surname, e.name, e.patronymic, wr.work_date, wr.hours_worked, e.salary * wr.hours_worked AS amount_earned " +
                "FROM work_records AS wr " +
                "INNER JOIN employees AS e " +
                "ON wr.employee_id = e.employee_id " +
                "ORDER BY wr.work_date DESC";

        return jdbcTemplate.query(query, (rs, rowNum) -> WorkRecordView.builder()
                .surname(rs.getString("surname"))
                .name(rs.getString("name"))
                .patronymic(rs.getString("patronymic"))
                .workDate(rs.getDate("work_date").toLocalDate())
                .hoursWorked(rs.getDouble("hours_worked"))
                .amountEarned(rs.getDouble("amount_earned"))
                .build());
    }

}
