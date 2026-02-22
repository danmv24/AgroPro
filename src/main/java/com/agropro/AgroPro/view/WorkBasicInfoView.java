package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkBasicInfoView {

    private final Long id;

    private final String workType;

    private final String status;

    private final Integer fieldNumber;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;
}
