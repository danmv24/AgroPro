package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping
    public List<FieldWithCurrentCropView> getFieldPlantings(@RequestParam(defaultValue = "2026") Integer year) {
        return fieldService.getFieldsWithCropByYear(year);
    }

}
