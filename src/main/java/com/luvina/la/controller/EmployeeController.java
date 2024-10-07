/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.payload.ParamConstants;
import com.luvina.la.payload.response.ErrorMessage;
import com.luvina.la.payload.response.ResponseCode;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.payload.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    public ApiResponse<List<EmployeeDTO>> getListEmployees(
            @RequestParam(name = "employee_name", required = false) String employeeName,
            @RequestParam(name = "department_id", required = false) String departmentId,
            @RequestParam(name = "ord_employee_name", required = false) String ordEmployeeName,
            @RequestParam(name = "ord_certification_name", required = false) String ordCertificationName,
            @RequestParam(name = "ord_end_date",required = false) String ordEndDate,
            @RequestParam(defaultValue = "0") String offset,
            @RequestParam(defaultValue = "5") String limit
    ) {
        if((!ParamConstants.ASC.getValue().equals(ordEmployeeName) && !ParamConstants.DESC.getValue().equals(ordEmployeeName)) ||
                (!ParamConstants.ASC.getValue().equals(ordCertificationName) && !ParamConstants.DESC.getValue().equals(ordCertificationName)) ||
                (!ParamConstants.ASC.getValue().equals(ordEndDate) && !ParamConstants.DESC.getValue().equals(ordEndDate))
        ) {
            return ApiResponse.<List<EmployeeDTO>>builder()
                    .code(ResponseCode.ERROR.getCode())
                    .message(ApiResponse.<ErrorMessage>builder()
                            .code(ErrorMessage.INVALID_ORDER_PARAMETER.getCode())
                            .params(ErrorMessage.INVALID_ORDER_PARAMETER.getParams())
                            .build())
                    .build();
        }

        if(Integer.parseInt(limit) < 0) {
            return ApiResponse.<List<EmployeeDTO>>builder()
                    .code(ResponseCode.ERROR.getCode())
                    .message(ApiResponse.<ErrorMessage>builder()
                            .code(ErrorMessage.INVALID_LIMIT_PARAMETER.getCode())
                            .params(ErrorMessage.INVALID_LIMIT_PARAMETER.getParams())
                            .build())
                    .build();
        }

        if(Integer.parseInt(offset) < 0) {
            return ApiResponse.<List<EmployeeDTO>>builder()
                    .code(ResponseCode.ERROR.getCode())
                    .message(ApiResponse.<ErrorMessage>builder()
                            .code(ErrorMessage.INVALID_OFFSET_PARAMETER.getCode())
                            .params(ErrorMessage.INVALID_OFFSET_PARAMETER.getParams())
                            .build())
                    .build();
        }

        return ApiResponse.<List<EmployeeDTO>>builder()
                .totalRecords(employeeService.countEmployees(employeeName, departmentId))
                .employees(employeeService.getEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offset, limit))
                .build();
    }
}
