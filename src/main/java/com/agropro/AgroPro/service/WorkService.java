package com.agropro.AgroPro.service;

import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.form.WorkResultForm;
import com.agropro.AgroPro.form.WorkUpdateForm;
import com.agropro.AgroPro.view.WorkByStatusView;
import com.agropro.AgroPro.view.WorkView;

import java.time.LocalDate;

public interface WorkService {

    void createWork(WorkForm workForm);

//    List<WorkBasicInfoView> getWorks();

    WorkView getWorkDetail(Long workId);

    void cancelWork(Long workId);

    void updateStatuses();

    void createResult(Long workId, WorkResultForm workResultForm);

    void updateWork(Long workId, WorkUpdateForm workUpdateForm);

//    Slice<WorkBasicInfoView> getCompletedWorksForWeek(LocalDate weekStart, int page, int size);

    WorkByStatusView getWorksByStatus(LocalDate weekStart, int page, int size);
}
