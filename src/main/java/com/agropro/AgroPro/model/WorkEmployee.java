package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "work_employees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkEmployee {

    @Id
    @Column("id")
    private Long id;

    @Column("work_id")
    private Long workId;

    @Column("employee_id")
    private Long employeeId;

}
