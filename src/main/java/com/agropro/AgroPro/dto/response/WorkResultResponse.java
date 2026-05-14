package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkResultResponse {

    private final List<WorkMaterialUsageResponse> materials;

    private final HarvestResponse harvest;

}
