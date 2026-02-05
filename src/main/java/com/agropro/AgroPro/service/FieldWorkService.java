package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.FieldWorkForm;
import com.agropro.AgroPro.view.FieldWorkBasicInfoView;
import com.agropro.AgroPro.view.FieldWorkView;

import java.util.List;

public interface FieldWorkService {

    void addFieldWork(FieldWorkForm fieldWorkForm);

    List<FieldWorkBasicInfoView> getFieldWorks();

    FieldWorkView getFieldWork(Long workId);

    void cancelFieldWork(Long workId);
}
