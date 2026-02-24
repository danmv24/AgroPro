package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.WorkForm;
import com.agropro.AgroPro.service.WorkService;
import com.agropro.AgroPro.view.WorkBasicInfoView;
import com.agropro.AgroPro.view.WorkView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/works")
public class WorkController {

    private final WorkService fieldWorkService;

    @PostMapping("/add")
    public ResponseEntity<Void> planWork(@Valid @RequestBody WorkForm fieldWorkForm) {
        fieldWorkService.createWork(fieldWorkForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<WorkBasicInfoView> getWorks() {
        return fieldWorkService.getWorks();
    }

    @GetMapping("/{workId}")
    public WorkView getWork(@PathVariable("workId") Long workId) {
        return fieldWorkService.getWork(workId);
    }

    @PostMapping("/cancel/{workId}")
    public ResponseEntity<Map<String, String>> cancelFieldWork(@PathVariable("workId") Long workId) {
        fieldWorkService.cancelWork(workId);
        return ResponseEntity.ok(Map.of("message", "Работа успешно отменена"));
     }

}
