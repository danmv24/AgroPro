package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.view.MachineryBasicInfoView;
import com.agropro.AgroPro.view.MachineryView;
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
public class JdbcNativeMachineryRepository implements MachineryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Machinery machinery) {
        String query = "INSERT INTO machineries(machinery_name, machinery_type_id, inventory_number, license_plate, current_status_id, purchase_date) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, machinery.getMachineryName());
            preparedStatement.setLong(2, machinery.getMachineryTypeId());
            preparedStatement.setInt(3, machinery.getInventoryNumber());
            preparedStatement.setString(4, machinery.getLicencePlate());
            preparedStatement.setLong(5, machinery.getCurrentStatusId());
            preparedStatement.setObject(6, machinery.getPurchaseDate());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("machinery_id")).longValue();
        machinery.setMachineryId(generatedId);

        return generatedId;
    }

    @Override
    public List<MachineryView> findAll() {
        String query = "SELECT m.machinery_name, mt.machinery_type, m.inventory_number, m.license_plate, sc.display_name" +
                " FROM machineries AS m " +
                "INNER JOIN machinery_types AS mt ON m.machinery_type_id = mt.id " +
                "INNER JOIN status_codes AS sc ON m.current_status_id = sc.status_id " +
                "ORDER BY m.machinery_name";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                     MachineryView.builder()
                             .machineryName(rs.getString("machinery_name"))
                             .machineryType(rs.getString("machinery_type"))
                             .inventoryNumber(rs.getInt("inventory_number"))
                             .licencePlate(rs.getString("license_plate"))
                             .statusCode(rs.getString("display_name"))
                             .build()
                );
    }

    @Override
    public List<MachineryBasicInfoView> findMachineriesWithIdleStatus() {
        String query = "SELECT m.machinery_id, m.machinery_name, mt.machinery_type, m.license_plate FROM machineries AS m " +
                "INNER JOIN machinery_types AS mt ON mt.id = m.machinery_type_id " +
                "INNER JOIN status_codes AS sc ON sc.status_id = m.current_status_id " +
                "WHERE sc.status_code = ?";

        return jdbcTemplate.query(query,
                ps -> ps.setString(1, "IDLE"),
                (rs, rowNum) ->
                    MachineryBasicInfoView.builder()
                            .machineryId(rs.getLong("machinery_id"))
                            .machineryName(rs.getString("machinery_name"))
                            .machineryType(rs.getString("machinery_type"))
                            .licensePlate(rs.getString("license_plate"))
                            .build()
                );
    }

    @Override
    public Set<Long> findExistingMachineriesByIds(Set<Long> machineryIds) {
        String param = String.join(",", Collections.nCopies(machineryIds.size(), "?"));
        String query = "SELECT machinery_id FROM machineries WHERE machinery_id IN(" + param + ")";
        List<Long> existingIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("machinery_id"),
                machineryIds.toArray());

        return new HashSet<>(existingIds);
    }

    @Override
    public Map<Long, Long> findMachineryStatusesByIds(Set<Long> machineryIds) {
        String param = String.join(",", Collections.nCopies(machineryIds.size(), "?"));
        String query = "SELECT machinery_id, current_status_id FROM machineries WHERE machinery_id IN(" + param + ");";

        return jdbcTemplate.query(query, rs -> {
            Map<Long, Long> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("machinery_id"), rs.getLong("current_status_id"));
            }
            return result;
        }, machineryIds.toArray());
    }

    @Override
    public List<Long> findConflictMachineryIdsByDateTime(Set<Long> machineryIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        String param = String.join(",", Collections.nCopies(machineryIds.size(), "?"));
        String query = "SELECT DISTINCT fwm.machinery_id FROM field_work_machineries AS fwm " +
                "INNER JOIN field_works AS fw ON fwm.field_work_id = fw.field_work_id " +
                "WHERE fwm.machinery_id IN(" + param + ") " +
                "AND (fw.end_date > ? AND fw.start_date < ?)";

        List<Object> paramsList = new ArrayList<>(machineryIds);
        paramsList.add(Timestamp.valueOf(startDateOfWork));
        paramsList.add(Timestamp.valueOf(endDateOfWork));

        List<Long> conflictMachineryIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("machinery_id"),
                paramsList.toArray());

        return conflictMachineryIds;
    }


}
