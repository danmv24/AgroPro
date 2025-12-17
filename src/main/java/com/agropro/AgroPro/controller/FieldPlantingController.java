package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.FieldPlantingForm;
import com.agropro.AgroPro.repository.CropRepository;
import com.agropro.AgroPro.repository.FieldRepository;
import com.agropro.AgroPro.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/field-plantings")
public class FieldPlantingController {

    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final FieldService fieldService;

    @GetMapping("/add")
    public String showAddFieldPlantingForm(Model model) {
        model.addAttribute("fields", fieldRepository.findAll());
        model.addAttribute("crops", cropRepository.findAll());

        return "fields/field_plantings_add";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addFieldPlanting(@RequestBody FieldPlantingForm fieldForm) {
        fieldService.addFieldPlanting(fieldForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Успешно"));
    }

}
