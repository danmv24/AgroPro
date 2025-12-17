package com.agropro.AgroPro.repository;

import com.agropro.AgroPro.view.CropView;

import java.util.List;

public interface CropRepository {

    List<CropView> findAll();

    boolean existsByCropId(Long cropId);

}
