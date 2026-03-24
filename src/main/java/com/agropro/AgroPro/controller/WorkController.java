package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.request.WorkRequest;
import com.agropro.AgroPro.dto.request.WorkResultRequest;
import com.agropro.AgroPro.dto.response.WorkBasicInfoResponse;
import com.agropro.AgroPro.dto.response.WorkResponse;
import com.agropro.AgroPro.enums.WorkStatus;
import com.agropro.AgroPro.service.WorkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/works")
public class WorkController {

    private final WorkService workService;

    @PostMapping("/add")
    public ResponseEntity<Void> planWork(@Valid @RequestBody WorkRequest workRequest) {
        workService.createWork(workRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{workId}")
    public WorkResponse getWork(@PathVariable("workId") Long workId) {
        return workService.getWorkDetail(workId);
    }

    @PostMapping("/cancel/{workId}")
    public ResponseEntity<Void> cancelFieldWork(@PathVariable("workId") Long workId) {
        workService.cancelWork(workId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/result/{workId}")
    public ResponseEntity<Void> addWorkResult(@PathVariable("workId") Long workId,
                                              @Valid @RequestBody WorkResultRequest workResultRequest) {
        workService.createResult(workId, workResultRequest);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/edit/{workId}")
//    public ResponseEntity<Void> editWork(@PathVariable("workId") Long workId, @Valid @RequestBody WorkUpdateForm workUpdateForm) {
//        workService.updateWork(workId, workUpdateForm);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/planned")
    public Slice<WorkBasicInfoResponse> getPlannedWorks(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "15") int size) {
        return workService.getWorksByStatus(WorkStatus.PLANNED, page, size);
    }

    @GetMapping("/in-progress")
    public Slice<WorkBasicInfoResponse> getInProgressWorks(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size) {
        return workService.getWorksByStatus(WorkStatus.IN_PROGRESS, page, size);
    }

    @GetMapping("/completed")
    public Slice<WorkBasicInfoResponse> getCompletedWorks(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "15") int size) {
        return workService.getWorksByStatus(WorkStatus.COMPLETED, page, size);
    }

}
