package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import com.agropro.AgroPro.model.Equipment;
import com.agropro.AgroPro.repository.EquipmentRepository;
import com.agropro.AgroPro.view.EquipmentBasicInfoView;
import com.agropro.AgroPro.view.EquipmentView;
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
public class JdbcNativeEquipmentRepository implements EquipmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long save(Equipment equipment) {
        String query = "INSERT INTO equipment(equipment_name, equipment_type_id, inventory_number, current_status_id, purchase_date) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, equipment.getEquipmentName());
            preparedStatement.setLong(2, equipment.getEquipmentTypeId());
            preparedStatement.setInt(3, equipment.getInventoryNumber());
            preparedStatement.setLong(4, equipment.getCurrentStatusId());
            preparedStatement.setObject(5, equipment.getPurchaseDate());

            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("equipment_id")).longValue();
        equipment.setEquipmentId(generatedId);

        return generatedId;
    }

    @Override
    public List<EquipmentView> findAll() {
        String query = "SELECT e.equipment_name, e.inventory_number, et.equipment_type, sc.display_name " +
                "FROM equipment AS e " +
                "INNER JOIN equipment_types AS et ON e.equipment_type_id = et.id " +
                "INNER JOIN status_codes AS sc ON e.current_status_id = sc.status_id " +
                "ORDER BY e.equipment_name";

        return jdbcTemplate.query(query, (rs, rowNum) ->
                    EquipmentView.builder()
                            .equipmentName(rs.getString("equipment_name"))
                            .equipmentType(rs.getString("equipment_type"))
                            .inventoryNumber(rs.getInt("inventory_number"))
                            .statusCode(rs.getString("display_name"))
                            .build()
                );
    }

    @Override
    public List<EquipmentBasicInfoView> findEquipmentWithIdleStatus() {
        String query = "SELECT e.equipment_id, e.equipment_name, et.equipment_type FROM equipment AS e " +
                "INNER JOIN equipment_types AS et ON et.id = e.equipment_type_id " +
                "INNER JOIN status_codes AS sc ON sc.status_id = e.current_status_id " +
                "WHERE sc.status_code = ?";

        return jdbcTemplate.query(query,
                ps -> ps.setString(1, "IDLE"),
                (rs, rowNum) ->
                        EquipmentBasicInfoView.builder()
                                .equipmentId(rs.getLong("equipment_id"))
                                .equipmentName(rs.getString("equipment_name"))
                                .equipmentType(rs.getString("equipment_type"))
                                .build()
        );
    }

    @Override
    public Set<Long> findExistingEquipmentByIds(Set<Long> equipmentIds) {
        String param = String.join(",", Collections.nCopies(equipmentIds.size(), "?"));
        String query = "SELECT equipment_id FROM equipment WHERE equipment_id IN(" + param + ")";
        List<Long> existingIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("equipment_id"),
                equipmentIds.toArray());

        return new HashSet<>(existingIds);
    }

    @Override
    public Map<Long, Long> findEquipmentStatusesByIds(Set<Long> equipmentIds) {
        String param = String.join(",", Collections.nCopies(equipmentIds.size(), "?"));
        String query = "SELECT equipment_id, current_status_id FROM equipment WHERE equipment_id IN(" + param + ")";

        return jdbcTemplate.query(query, rs -> {
            Map<Long, Long> result = new HashMap<>();
            while (rs.next()) {
                result.put(rs.getLong("equipment_id"), rs.getLong("current_status_id"));
            }
            return result;
        }, equipmentIds.toArray());
    }

    @Override
    public List<Long> findConflictEquipmentIdsByDateTime(Set<Long> equipmentIds, LocalDateTime startDateOfWork, LocalDateTime endDateOfWork) {
        String param = String.join(",", Collections.nCopies(equipmentIds.size(), "?"));
        String query = "SELECT DISTINCT fwe.equipment_id FROM field_work_equipment AS fwe " +
                "INNER JOIN field_works AS fw ON fwe.field_work_id = fw.field_work_id " +
                "WHERE fwe.equipment_id IN(" + param + ") " +
                "AND fw.status IN(?, ?)" +
                "AND (fw.end_date > ? AND fw.start_date < ?)";

        List<Object> paramsList = new ArrayList<>(equipmentIds);
        paramsList.add(FieldWorkStatus.PLANNED.name());
        paramsList.add(FieldWorkStatus.IN_PROGRESS.name());
        paramsList.add(Timestamp.valueOf(startDateOfWork));
        paramsList.add(Timestamp.valueOf(endDateOfWork));


        List<Long> conflictEquipmentIds = jdbcTemplate.query(query,
                (rs, rowNum) -> rs.getLong("equipment_id"),
                paramsList.toArray());

        return conflictEquipmentIds;
    }

    @Override
    public List<EquipmentBasicInfoView> findEquipmentByFieldWorkId(Long workId) {
        String query = "SELECT e.equipment_id, e.equipment_name, et.equipment_type FROM field_work_equipment AS fwe " +
                "INNER JOIN equipment AS e ON e.equipment_id = fwe.equipment_id " +
                "INNER JOIN equipment_types AS et ON et.id = e.equipment_type_id " +
                "WHERE fwe.field_work_id = ?";

        return jdbcTemplate.query(query, (rs, rowNum) -> EquipmentBasicInfoView.builder()
                .equipmentId(rs.getLong("equipment_id"))
                .equipmentName(rs.getString("equipment_name"))
                .equipmentType(rs.getString("equipment_type"))
                .build(),
                workId);
    }


}
