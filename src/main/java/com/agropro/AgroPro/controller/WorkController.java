package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.form.WorkResultForm;
import com.agropro.AgroPro.form.WorkUpdateForm;
import com.agropro.AgroPro.service.WorkService;
import com.agropro.AgroPro.view.WorkByStatusView;
import com.agropro.AgroPro.view.WorkView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/works")
public class WorkController {

    private final WorkService workService;

    @PostMapping("/add")
    public ResponseEntity<Void> planWork(@Valid @RequestBody WorkForm workForm) {
        workService.createWork(workForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping
//    public List<WorkBasicInfoView> getWorks() {
//        return workService.getWorks();
//    }

    @GetMapping("/{workId}")
    public WorkView getWork(@PathVariable("workId") Long workId) {
        return workService.getWorkDetail(workId);
    }

    @PostMapping("/cancel/{workId}")
    public ResponseEntity<Void> cancelFieldWork(@PathVariable("workId") Long workId) {
        workService.cancelWork(workId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/result/{workId}")
    public ResponseEntity<Void> addWorkResult(@PathVariable("workId") Long workId,
                                              @Valid @RequestBody WorkResultForm workResultForm) {
        workService.createResult(workId, workResultForm);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit/{workId}")
    public ResponseEntity<Void> editWork(@PathVariable("workId") Long workId, @Valid @RequestBody WorkUpdateForm workUpdateForm) {
        workService.updateWork(workId, workUpdateForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public WorkByStatusView getWorksForWeek(@RequestParam LocalDate weekStart,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "15") int size) {
        return workService.getWorksByStatus(weekStart, page, size);
    }

}
