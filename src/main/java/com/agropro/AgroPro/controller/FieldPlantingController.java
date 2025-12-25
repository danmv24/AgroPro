package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldPlantingService;
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

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final FieldPlantingService fieldPlantingService;

    @GetMapping("/add")
    public String showAddFieldPlantingForm(Model model) {
        model.addAttribute("fields", fieldRepository.findAll());
        model.addAttribute("crops", cropRepository.findAll());

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
