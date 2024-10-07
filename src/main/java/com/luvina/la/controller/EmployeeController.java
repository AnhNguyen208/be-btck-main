/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.payload.ParamConstants;
import com.luvina.la.payload.ErrorMessage;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.payload.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller để quản lý employee
 * @author AnhNLT
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class EmployeeController {
    EmployeeService employeeService;

    /**
     * Api lấy danh sách employee
     * @param employeeName Tên nhân viên
     * @param departmentId Mã phòng ban
     * @param ordEmployeeName ASC hoặc DESC
     * @param ordCertificationName ASC hoặc DESC
     * @param ordEndDate ASC hoặc DESC
     * @param offset Số nguyên dương
     * @param limit Số nguyên dương
     * @return Danh sách EmployeeDTO
     *
     */
    @GetMapping
    public ResponseEntity<?> getListEmployees(
            @RequestParam(name = "employee_name", required = false) String employeeName,
            @RequestParam(name = "department_id", required = false) String departmentId,
            @RequestParam(name = "ord_employee_name", required = false) String ordEmployeeName,
            @RequestParam(name = "ord_certification_name", required = false) String ordCertificationName,
            @RequestParam(name = "ord_end_date",required = false) String ordEndDate,
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "5") String limit
    ) {
        if(checkOrdValue(ordEmployeeName) || checkOrdValue(ordCertificationName) || checkOrdValue((ordEndDate))) {
            ApiResponse<?> response = ApiResponse.createErrorResponse(ErrorMessage.INVALID_ORDER_PARAMETER);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(Integer.parseInt(limit) < 0) {
            ApiResponse<?> response = ApiResponse.createErrorResponse(ErrorMessage.INVALID_LIMIT_PARAMETER);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(Integer.parseInt(offset) < 0) {
            ApiResponse<?> response = ApiResponse.createErrorResponse(ErrorMessage.INVALID_OFFSET_PARAMETER);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Long totalRecords = employeeService.countEmployees(employeeName, departmentId);
        List<EmployeeDTO> list = employeeService.getEmployees(employeeName, departmentId,
                ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

        ApiResponse<List<EmployeeDTO>> response = ApiResponse.<List<EmployeeDTO>>builder()
                .totalRecords(totalRecords)
                .employees(list)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean checkOrdValue(String value) {
        return (!ParamConstants.ASC.getValue().equals(value)) && !ParamConstants.DESC.getValue().equals(value);
    }
}
