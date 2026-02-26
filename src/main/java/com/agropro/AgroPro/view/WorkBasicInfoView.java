package com.agropro.AgroPro.view;

import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.enums.WorkType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkBasicInfoView {

    private final Long id;

    private final WorkType workType;

    private final WorkStatus status;

    private final Integer fieldNumber;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final boolean hasResult;
}
