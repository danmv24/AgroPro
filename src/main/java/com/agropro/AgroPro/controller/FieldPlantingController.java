package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.service.CropService;
import com.agropro.AgroPro.service.FieldPlantingService;
import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldPlantingView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/field-plantings")
public class FieldPlantingController {

    private final CropService cropService;
    private final FieldService fieldService;
    private final FieldPlantingService fieldPlantingService;

    @GetMapping("/add")
    public String showAddFieldPlantingForm(Model model) {
        model.addAttribute("fields", fieldService.getAllFields());
        model.addAttribute("crops", cropService.getAllCrops());

        return "fields/field_plantings_add";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addFieldPlanting(@RequestBody FieldPlantingForm fieldForm) {
        fieldPlantingService.addFieldPlanting(fieldForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Успешно"));
    }

    @GetMapping("/by-field/{fieldId}/data")
    @ResponseBody
    public List<FieldPlantingView> getFieldPlantingsDataByFieldId(@PathVariable Long fieldId) {
        return fieldPlantingService.getFieldPlantingsByFieldId(fieldId);
    }

}
