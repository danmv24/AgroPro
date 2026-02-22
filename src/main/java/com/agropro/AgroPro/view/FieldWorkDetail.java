package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class FieldWorkDetail {

    private final Long fieldWorkId;

    private final String workType;

    private final String status;

    private final Integer fieldNumber;

    private final String description;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

}
