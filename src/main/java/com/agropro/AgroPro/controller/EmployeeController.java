package com.agropro.AgroPro.controller;

import com.agropro.AgroPro.form.EmployeeForm;
import com.agropro.AgroPro.repository.PositionRepository;
import com.agropro.AgroPro.service.EmployeeService;
import com.agropro.AgroPro.view.EmployeeView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionRepository positionRepository;

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("positions", positionRepository.findAll());
        return "employees/employee_add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        employeeService.addEmployee(employeeForm);
        return "redirect:/employees/success";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "employees/employee_success";
    }

    @GetMapping("/list")
    public String getAllEmployees(Model model) {
        List<EmployeeView> employeeViews = employeeService.getEmployees();
        model.addAttribute("employeeView", employeeViews);
        return "employees/employee_list";
    }
}
