package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.exception.WorkTypeNotFoundException;
import com.agropro.AgroPro.mapper.WorkTypeMapper;
import com.agropro.AgroPro.model.WorkType;
import com.agropro.AgroPro.repository.WorkTypeRepository;
import com.agropro.AgroPro.service.WorkTypeService;
import com.agropro.AgroPro.view.WorkTypeView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultWorkTypeService implements WorkTypeService {

    private final WorkTypeRepository workTypeRepository;

    @Override
    public List<WorkTypeView> getAllWorkTypes() {
        List<WorkType> workTypes = workTypeRepository.findAll();

        return workTypes.stream()
                .map(WorkTypeMapper::toView)
                .toList();
    }

    @Override
    public void validateWorkTypeExistById(Long workTypeId) {
        if (!workTypeRepository.existWorkTypeById(workTypeId)) {
            throw new WorkTypeNotFoundException(HttpStatus.NOT_FOUND, workTypeId);
        }
    }
}
