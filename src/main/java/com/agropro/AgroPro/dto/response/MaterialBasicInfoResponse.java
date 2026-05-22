package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class MaterialBasicInfoResponse {

    private final Long id;

    private final String materialName;

}
