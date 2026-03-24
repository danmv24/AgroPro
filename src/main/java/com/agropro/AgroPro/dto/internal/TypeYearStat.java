package com.agropro.AgroPro.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeYearStat {

    private int atStartDateCount;

    private int incomingCount;

    private int decommissionedCount;

    private int atEndDateCount;

}
