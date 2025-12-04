package com.agropro.AgroPro.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(name = "employees_work_time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkRecord {

    @Id
    private Long id;

    @Column("employee_id")
    private Long employeeId;

    @Column("work_date")
    private LocalDate workDate;

    @Column("hours_worked")
    private Double hoursWorked;

}
