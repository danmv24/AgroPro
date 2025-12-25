package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.service.FieldService;
import com.agropro.AgroPro.view.FieldWithCurrentCropView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fields")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping("/list")
    public String getAllPlantings(Model model) {
        return "fields/fields_list";
    }

    @GetMapping("/data-json")
    @ResponseBody
    public List<FieldWithCurrentCropView> getFieldPlantingsDataJson(@RequestParam(defaultValue = "2025") Integer year) {
        return fieldService.getFieldsWithCropByYear(year);
    }

}
