package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.CropNotFoundException;
import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCropService implements CropService {

    private final CropRepository cropRepository;

    @Override
    public void validateCropExistsById(Long cropId) {
        if (!cropRepository.existsByCropId(cropId)) {
            throw new CropNotFoundException(HttpStatus.NOT_FOUND, cropId);
        }
    }
}
