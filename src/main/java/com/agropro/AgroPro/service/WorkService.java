package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.request.WorkResultRequest;
import com.agropro.AgroPro.dto.response.WorkBasicInfoResponse;
import com.agropro.AgroPro.dto.response.WorkResponse;
import com.agropro.AgroPro.enums.WorkStatus;
import org.springframework.data.domain.Slice;

public interface WorkService {

    void createWork(WorkRequest workRequest);

//    List<WorkBasicInfoView> getWorks();

    WorkResponse getWorkDetail(Long workId);

    void cancelWork(Long workId);

    void updateStatuses();

    void createResult(Long workId, WorkResultRequest workResultRequest);

//    void updateWork(Long workId, WorkUpdateForm workUpdateForm);

//    Slice<WorkBasicInfoView> getCompletedWorksForWeek(LocalDate weekStart, int page, int size);

    Slice<WorkBasicInfoResponse> getWorksByStatus(WorkStatus workStatus, int page, int size);
}
