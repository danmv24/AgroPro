package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;

@Builder
@Getter
@RequiredArgsConstructor
public class WorkByStatusView {

    private final Slice<WorkBasicInfoView> planned;

    private final Slice<WorkBasicInfoView> inProgress;

    private final Slice<WorkBasicInfoView> completed;

}
