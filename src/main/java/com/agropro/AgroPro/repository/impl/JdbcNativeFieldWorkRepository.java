package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.model.FieldWork;
import com.agropro.AgroPro.repository.FieldWorkRepository;
import com.agropro.AgroPro.view.FieldWorkBasicInfoView;
import com.agropro.AgroPro.view.FieldWorkDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
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

        List<Object[]> arguments = machineryIds.stream()
                .map(machineryId -> new Object[]{workId, machineryId})
                .toList();

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

    @Override
    public List<FieldWorkBasicInfoView> findAll() {
        String query = "SELECT fw.field_work_id, wt.work_name, f.field_number, fw.start_date, fw.end_date, fw.status FROM field_works AS fw " +
                "INNER JOIN work_types AS wt ON fw.work_type_id = wt.work_type_id " +
                "INNER JOIN fields AS f ON fw.field_id = f.field_id " +
                "ORDER BY fw.start_date, fw.end_date";

        return jdbcTemplate.query(query, (rs, rowNum) -> FieldWorkBasicInfoView.builder()
                .fieldWorkId(rs.getLong("field_work_id"))
                .fieldNumber(rs.getInt("field_number"))
                .workTypeName(rs.getString("work_name"))
                .status(rs.getString("status"))
                .startDate(rs.getTimestamp("start_date").toLocalDateTime())
                .endDate(rs.getTimestamp("end_date").toLocalDateTime())
                .build());
    }

    @Override
    public Optional<FieldWorkDetail> findFieldWorkById(Long workId) {
        String query = "SELECT fw.field_work_id, wt.work_name, f.field_number, fw.status, fw.description, fw.start_date, fw.end_date " +
                "FROM field_works AS fw " +
                "INNER JOIN work_types AS wt ON fw.work_type_id = wt.work_type_id " +
                "INNER JOIN fields AS f ON f.field_id = fw.field_id " +
                "WHERE fw.field_work_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, (rs, rowNum) -> FieldWorkDetail.builder()
                        .fieldWorkId(rs.getLong("field_work_id"))
                        .workTypeName(rs.getString("work_name"))
                        .fieldNumber(rs.getInt("field_number"))
                        .status(rs.getString("status"))
                        .description(rs.getString("description"))
                        .startDate(rs.getTimestamp("start_date").toLocalDateTime())
                        .endDate(rs.getTimestamp("end_date").toLocalDateTime())
                        .build(),
                workId));
    }

    @Override
    public void updateStatusById(Long workId, FieldWorkStatus status) {
        String query = "UPDATE field_works SET status = ? WHERE field_work_id = ?";
        jdbcTemplate.update(query, status.name(), workId);
    }

    @Override
    public boolean existByFieldWorkId(Long workId) {
        String query = "SELECT EXISTS(SELECT 1 FROM field_works WHERE field_work_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, workId));
    }

    @Override
    public Optional<FieldWorkStatus> getStatusByFieldWorkId(Long workId) {
        String query = "SELECT status FROM field_works WHERE field_work_id = ?";
        String result = jdbcTemplate.queryForObject(query, String.class, workId);

        if (result == null) {
            return Optional.empty();
        }

        return Optional.of(FieldWorkStatus.valueOf(result));
    }
}
