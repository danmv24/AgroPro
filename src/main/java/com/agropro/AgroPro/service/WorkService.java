package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.WorkForm;
import com.agropro.AgroPro.dto.request.WorkResultForm;
import com.agropro.AgroPro.dto.response.WorkByStatusResponse;
import com.agropro.AgroPro.dto.response.WorkResponse;

import java.time.LocalDate;

public interface WorkService {

    void createWork(WorkForm workForm);

//    List<WorkBasicInfoView> getWorks();

    WorkResponse getWorkDetail(Long workId);

    void cancelWork(Long workId);

    void updateStatuses();

    void createResult(Long workId, WorkResultForm workResultForm);

//    void updateWork(Long workId, WorkUpdateForm workUpdateForm);

//    Slice<WorkBasicInfoView> getCompletedWorksForWeek(LocalDate weekStart, int page, int size);

    WorkByStatusResponse getWorksByStatus(LocalDate weekStart, int page, int size);
}
