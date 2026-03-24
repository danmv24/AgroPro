package com.agropro.AgroPro.projection;

import com.agropro.AgroPro.enums.WorkType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WorkTypeHours {

    private final WorkType workType;

    private final Double totalHours;

}
