package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.entity.Employee;
import com.luvina.la.payload.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class EmployeeController {
    EmployeeService employeeService;
    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getListEmployees(@RequestParam String employee_name,
                                                           String department_id, String ord_employee_name,
                                                           String ord_certification_name, String ord_end_date,
                                                           String offset, String limit) {

        return ApiResponse.<List<EmployeeDTO>>builder()
                .totalRecords(employeeService.countEmployees(employee_name, department_id))
                .employees(employeeService.getEmployees(employee_name, department_id, ord_employee_name, ord_certification_name, ord_end_date, offset, limit))
                .build();
    }
}
