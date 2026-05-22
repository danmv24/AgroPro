package com.agropro.AgroPro.dto.response;

import com.agropro.AgroPro.enums.WorkType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class FieldWorkResponse {

    private final WorkType workType;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}
