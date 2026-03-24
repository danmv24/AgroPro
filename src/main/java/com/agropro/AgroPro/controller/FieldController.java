package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.dto.response.FieldResponse;
import com.agropro.AgroPro.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping
    public List<FieldResponse> getFieldPlantings(@RequestParam(required = false) LocalDate date) {
        LocalDate requiredDate = date != null ? date : LocalDate.now();
        return fieldService.getFieldsWithCropByDate(requiredDate);
    }

}
