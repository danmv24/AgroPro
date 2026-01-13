package com.agropro.AgroPro.repository.impl;

import com.agropro.AgroPro.model.Machinery;
import com.agropro.AgroPro.repository.MachineryRepository;
import com.agropro.AgroPro.view.MachineryView;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNativeMachineryRepository implements MachineryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Machinery machinery) {
        String query = "INSERT INTO machineries(machinery_name, machinery_type_id, inventory_number, license_plate, status_id) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, machinery.getMachineryName());
            preparedStatement.setLong(2, machinery.getMachineryTypeId());
            preparedStatement.setInt(3, machinery.getInventoryNumber());
            preparedStatement.setString(4, machinery.getLicencePlate());
            preparedStatement.setLong(5, machinery.getStatusId());
            return preparedStatement;
        }, keyHolder);

        Long generatedId = ((Number) keyHolder.getKeys().get("machinery_id")).longValue();
        machinery.setMachineryId(generatedId);
    }

    @Override
    public List<MachineryView> findAll() {
        String query = "SELECT m.machinery_name, mt.machinery_type, m.inventory_number, m.license_plate, sc.display_name" +
                " FROM machineries AS m " +
                "INNER JOIN machinery_types AS mt ON m.machinery_type_id = mt.id " +
                "INNER JOIN status_codes AS sc ON m.status_id = sc.status_id " +
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
}
