package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.view.WorkBasicInfoView;
import com.agropro.AgroPro.view.WorkView;

import java.util.List;

public interface WorkService {

    void createWork(WorkForm workForm);

    List<WorkBasicInfoView> getWorks();

    WorkView getWork(Long workId);

    void cancelWork(Long workId);

    void updateStatuses();
}
