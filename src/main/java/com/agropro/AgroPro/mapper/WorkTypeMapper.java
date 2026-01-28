package com.agropro.AgroPro.mapper;

import com.agropro.AgroPro.model.WorkType;
import com.agropro.AgroPro.view.WorkTypeView;

public class WorkTypeMapper {

    public static WorkTypeView toView(WorkType workType) {
        return WorkTypeView.builder()
                .workTypeId(workType.getWorkTypeId())
                .workName(workType.getWorkName())
                .build();
    }

}
