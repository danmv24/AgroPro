package com.agropro.AgroPro.service;

import com.agropro.AgroPro.view.CropView;

import java.util.List;

public interface CropService {

    void validateCropExistsById(Long cropId);

    List<CropView> getAllCrops();

}