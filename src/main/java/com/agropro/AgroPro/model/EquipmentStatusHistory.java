package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.StatusCode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "equipment_status_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentStatusHistory {

    @Id
    private Long id;

    @Column("equipment_id")
    private Long equipmentId;

    @Column("status")
    private StatusCode status;

    @Column("changed_at")
    private LocalDateTime changedAt;

}
