package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.FieldWorkStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "field_works")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FieldWork {

    @Id
    @Column("field_work_id")
    private Long fieldWorkId;

    @Column("work_type_id")
    private Long workTypeId;

    @Column("field_id")
    private Long fieldId;

    @Column("status")
    private FieldWorkStatus status;

    @Column("planned_start_date")
    private LocalDateTime startDate;

    @Column("planned_end_date")
    private LocalDateTime endDate;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
