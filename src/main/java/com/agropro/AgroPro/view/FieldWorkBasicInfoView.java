package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class FieldWorkBasicInfoView {

    private final Long fieldWorkId;

    private final String workTypeName;

    private final String status;

    private final Integer fieldNumber;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;
}
