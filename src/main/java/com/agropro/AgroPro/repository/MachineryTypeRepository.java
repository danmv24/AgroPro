package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.model.MachineryType;

import java.util.List;

public interface MachineryTypeRepository {

    List<MachineryType> findAll();

    boolean existsById(Long id);

}
