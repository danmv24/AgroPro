package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class FieldBasicInfoResponse {

    private final Long id;

    private final Integer fieldNumber;

}
