package com.agropro.AgroPro.view;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class WorkTypeView {

    private final Long workTypeId;

    private final String workName;

}
