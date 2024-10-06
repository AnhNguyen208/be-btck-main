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

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class EmployeeController {
    EmployeeService employeeService;
    @GetMapping
    public ApiResponse<List<EmployeeDTO>> getListEmployees(@RequestParam(required = false) String employee_name,
                                                           @RequestParam(required = false) String department_id,
                                                           @RequestParam(required = false) String ord_employee_name,
                                                           @RequestParam(required = false) String ord_certification_name,
                                                           @RequestParam(required = false) String ord_end_date,
                                                           @RequestParam(defaultValue = "0") String offset,
                                                           @RequestParam(defaultValue = "5") String limit) {
        if((!ParamConstants.ASC.getValue().equals(ord_employee_name) && !ParamConstants.DESC.getValue().equals(ord_employee_name)) ||
                (!ParamConstants.ASC.getValue().equals(ord_certification_name) && !ParamConstants.DESC.getValue().equals(ord_certification_name)) ||
                (!ParamConstants.ASC.getValue().equals(ord_end_date) && !ParamConstants.DESC.getValue().equals(ord_end_date))
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
                .totalRecords(employeeService.countEmployees(employee_name, department_id))
                .employees(employeeService.getEmployees(employee_name, department_id, ord_employee_name, ord_certification_name, ord_end_date, offset, limit))
                .build();
    }
}
