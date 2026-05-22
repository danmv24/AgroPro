package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.response.FieldBasicInfoResponse;
import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    private final FieldPlantingService fieldPlantingService;

    @GetMapping
    public List<FieldResponse> getFieldPlantings(@RequestParam(required = false) LocalDate date) {
        LocalDate requiredDate = date != null ? date : LocalDate.now();
        return fieldService.getFieldsWithCropByDate(requiredDate);
    }

    @GetMapping("/list")
    public List<FieldBasicInfoResponse> getFields() {
        return fieldService.getFields();
    }

    @GetMapping("/{fieldId}/plantings")
    public Slice<FieldPlantingResponse> getPlantingHistory(@PathVariable Long fieldId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return fieldPlantingService.getFieldPlantingHistory(fieldId, page, size);
    }

}
