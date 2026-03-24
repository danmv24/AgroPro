package com.agropro.AgroPro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkByStatusResponse {

    private final Slice<WorkBasicInfoResponse> planned;

    private final Slice<WorkBasicInfoResponse> inProgress;

    private final Slice<WorkBasicInfoResponse> completed;

}
