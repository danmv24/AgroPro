package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.internal.MaterialInternalData;
import com.agropro.AgroPro.dto.request.MaterialRequest;
import com.agropro.AgroPro.dto.request.MaterialUpdateRequest;
import com.agropro.AgroPro.dto.response.MaterialBasicInfoResponse;
import com.agropro.AgroPro.dto.response.MaterialResponse;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Set;

public interface MaterialService {

    void createMaterial(MaterialRequest materialRequest);

    Slice<MaterialResponse> getMaterials(int page, int size);

    void updateMaterial(Long id, MaterialUpdateRequest materialUpdateRequest);

    MaterialResponse getMaterialById(Long id);

    List<MaterialInternalData> getMaterialsByIds(Set<Long> materialIds);

    List<MaterialBasicInfoResponse> getMaterialsList();
}
