package com.agropro.AgroPro.service;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.request.WorkResultRequest;
import com.agropro.AgroPro.dto.response.WorkBasicInfoResponse;
import com.agropro.AgroPro.dto.response.WorkResponse;
import com.agropro.AgroPro.enums.WorkStatus;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface WorkService {

    void createWork(WorkRequest workRequest);

    WorkResponse getWorkDetail(Long workId);

    void cancelWork(Long workId);

    void updateStatuses();

    void createResult(Long workId, WorkResultRequest workResultRequest);

    Slice<WorkBasicInfoResponse> getWorksByStatus(WorkStatus workStatus, int page, int size);

    List<WorkBasicInfoResponse> getAssignedPlannedWorks(int page, int size);
}
