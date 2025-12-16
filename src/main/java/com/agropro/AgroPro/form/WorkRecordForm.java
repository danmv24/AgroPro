package com.agropro.AgroPro.form;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkRecordForm {

    private Long employeeId;

    private LocalDate workDate;

    private BigDecimal hoursWorked;

}
