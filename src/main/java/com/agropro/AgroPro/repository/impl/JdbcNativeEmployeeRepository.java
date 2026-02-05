package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.enums.PaymentType;
import com.agropro.AgroPro.model.Employee;
import com.agropro.AgroPro.repository.EmployeeRepository;
import com.agropro.AgroPro.view.EmployeeBasicInfoView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcNativeEmployeeRepository implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Employee employee) {
        String query = "INSERT INTO employees (surname, name, patronymic, position_id, payment_type, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, employee.getSurname());
            preparedStatement.setString(2, employee.getName());
            preparedStatement.setString(3, employee.getPatronymic());
            preparedStatement.setLong(4, employee.getPositionId());
            preparedStatement.setString(5, String.valueOf(employee.getPaymentType()));
            preparedStatement.setBigDecimal(6, employee.getSalary());
            preparedStatement.setObject(7, employee.getHireDate());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("employee_id")).longValue();
        employee.setEmployeeId(generatedId);
    }

    @Override
    public List<Employee> findAll() {
        String query = "SELECT employee_id, surname, name, patronymic, position_id, payment_type, salary FROM employees";
        return jdbcTemplate.query(query,
                (rs, rowNum) -> Employee.builder()
                        .employeeId(rs.getLong("employee_id"))
                        .surname(rs.getString("surname"))
                        .name(rs.getString("name"))
                        .patronymic(rs.getString("patronymic"))
                        .positionId(rs.getLong("position_id"))
                        .paymentType(PaymentType.valueOf(rs.getString("payment_type")))
                        .salary(rs.getBigDecimal("salary"))
                        .build()
                );
    }

    @Override
    public List<EmployeeBasicInfoView> findEmployeesWherePaymentTypeIsHourly() {
        String query = "SELECT employee_id, surname, name, patronymic FROM employees WHERE payment_type = ? ORDER BY surname, name";
        return jdbcTemplate.query(query,
                ps -> ps.setString(1, "HOURLY"),
                (rs, rowNum) -> EmployeeBasicInfoView.builder()
                        .employeeId(rs.getLong("employee_id"))
                        .surname(rs.getString("surname"))
                        .name(rs.getString("name"))
                        .patronymic(rs.getString("patronymic"))
                        .build()
                );
    }

    @Override
    public boolean existsByEmployeeId(Long employeeId) {
        String query = "SELECT EXISTS(SELECT 1 FROM employees WHERE employee_id = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(query, Boolean.class, employeeId));
    }

    @Override
    public List<EmployeeBasicInfoView> findEmployeesWherePositionIsMechanizator() {
        String query = "SELECT e.employee_id, e.surname, e.name, e.patronymic FROM employees AS e " +
                "INNER JOIN positions AS p ON p.position_id = e.position_id " +
                "WHERE p.position_name = ?";

        return jdbcTemplate.query(query,
                ps -> ps.setString(1, "Механизатор"),
                (rs, rowNum) -> EmployeeBasicInfoView.builder()
                            .employeeId(rs.getLong("employee_id"))
                            .surname(rs.getString("surname"))
                            .name(rs.getString("name"))
                            .patronymic(rs.getString("patronymic"))
                            .build()
                );
    }

//    @Override
//    public boolean existsByEmployeeIds(Set<Long> employeeIds) {
//        String query = "SELECT COUNT(employee_id) FROM employees WHERE employee_id IN(" +
//                employeeIds.stream().map(employeeId -> "?").collect(Collectors.joining(",")) +
//                ")";
//        Integer count = jdbcTemplate.queryForObject(query, Integer.class, employeeIds.toArray());
//
//        return count != null && count == employeeIds.size();
//    }

    @Override
    public Set<Long> findExistingEmployeesByIds(Set<Long> employeeIds) {
        String param = String.join(",", Collections.nCopies(employeeIds.size(), "?"));
        String query = "SELECT employee_id FROM employees WHERE employee_id IN (" + param + ")";
        List<Long> existingIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("employee_id"),
                employeeIds.toArray());

        return new HashSet<>(existingIds);
    }

    @Override
    public List<Long> findConflictEmployeeIdsByDateTime(Set<Long> employeeIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        String param = String.join(",", Collections.nCopies(employeeIds.size(), "?"));
        String query = "SELECT DISTINCT fwe.employee_id FROM field_work_employees AS fwe " +
                "INNER JOIN field_works AS fw ON fwe.field_work_id = fw.field_work_id " +
                "WHERE fwe.employee_id IN(" + param + ") " +
                "AND fw.status IN(?, ?) " +
                "AND (fw.end_date > ? AND fw.start_date < ?)";

        List<Object> paramsList = new ArrayList<>(employeeIds);
        paramsList.add(FieldWorkStatus.PLANNED.name());
        paramsList.add(FieldWorkStatus.IN_PROGRESS.name());
        paramsList.add(Timestamp.valueOf(startDateOfWork));
        paramsList.add(Timestamp.valueOf(endDateOfWork));

        List<Long> conflictEmployeeIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("employee_id"),
                paramsList.toArray());

        return conflictEmployeeIds;
    }

    @Override
    public List<EmployeeBasicInfoView> findEmployeesByFieldWorkId(Long workId) {
        String query = "SELECT e.employee_id, e.surname, e.name, e.patronymic FROM field_work_employees AS fwe " +
                "INNER JOIN employees AS e ON e.employee_id = fwe.employee_id " +
                "WHERE fwe.field_work_id = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> EmployeeBasicInfoView.builder()
                .employeeId(rs.getLong("employee_id"))
                .surname(rs.getString("surname"))
                .name(rs.getString("patronymic"))
                .build(), workId);
    }


}
