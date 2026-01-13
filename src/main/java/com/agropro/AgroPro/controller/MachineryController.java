package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.MachineryForm;
import com.agropro.AgroPro.service.MachineryService;
import com.agropro.AgroPro.service.MachineryTypeService;
import com.agropro.AgroPro.view.MachineryView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/machineries")
public class MachineryController {

    private final MachineryTypeService machineryTypeService;
    private final MachineryService machineryService;

    @GetMapping("/add")
    public String showAddMachineryForm(Model model) {
        model.addAttribute("machineryForm", new MachineryForm());
        model.addAttribute("machineryTypes", machineryTypeService.getAllMachineryTypes());
        return "machineries/machinery_add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, String>> addMachinery(@RequestBody MachineryForm machineryForm) {
        machineryService.addMachinery(machineryForm);
        return ResponseEntity.ok(Map.of("status", "success", "message", "Техника успешно добавлена"));
    }

    @GetMapping("/list")
    public String showMachineries(Model model) {
        List<MachineryView> machineryViews = machineryService.getMachineries();
        model.addAttribute("machineries", machineryViews);

        return "machineries/machinery_list";
    }

}
