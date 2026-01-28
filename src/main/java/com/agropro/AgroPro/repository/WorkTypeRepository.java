package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.WorkType;

import java.util.List;

public interface WorkTypeRepository {

    List<WorkType> findAll();

}
