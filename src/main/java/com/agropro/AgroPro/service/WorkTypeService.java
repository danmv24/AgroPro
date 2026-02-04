package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.WorkTypeView;

import java.util.List;

public interface WorkTypeService {

    List<WorkTypeView> getAllWorkTypes();

    void validateWorkTypeExistById(Long workTypeId);

}
