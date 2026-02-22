package com.agropro.AgroPro.model;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "works")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Work {

    @Id
    private Long id;

    @Column("work_type")
    private WorkType workType;

    @Column("field_id")
    private Long fieldId;

    @Column("status")
    private WorkStatus status;

    @Column("description")
    private String description;

    @Column("start_date")
    private LocalDateTime startDate;

    @Column("end_date")
    private LocalDateTime endDate;

}
