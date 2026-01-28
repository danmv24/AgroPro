package com.agropro.AgroPro.service.impl;

import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.repository.FieldWorkRepository;
import com.agropro.AgroPro.service.FieldWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFieldWorkService implements FieldWorkService {

    private final FieldWorkRepository fieldWorkRepository;

    @Override
    public void addFieldWork(FieldWorkForm fieldWorkForm) {

    }


}
