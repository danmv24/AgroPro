package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class ProductionPlanBasicResponse {

    private final Long id;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final LocalDateTime createdAt;

}
