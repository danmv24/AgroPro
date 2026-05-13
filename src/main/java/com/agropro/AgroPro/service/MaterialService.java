package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.MaterialRequest;
import com.agropro.AgroPro.dto.request.MaterialUpdateRequest;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import com.agropro.AgroPro.model.Material;
import org.springframework.data.domain.Slice;

public interface MaterialService {

    void createMaterial(MaterialRequest materialRequest);

    Slice<MaterialResponse> getMaterials(int page, int size);

    void updateMaterial(Long id, MaterialUpdateRequest materialUpdateRequest);

    Material getMaterialById(Long id);

}
