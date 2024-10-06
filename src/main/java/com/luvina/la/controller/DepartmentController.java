/**
 * Copyright(C) 2024  Luvina
 * DepartmentController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.payload.response.ErrorMessage;
import com.luvina.la.payload.response.ResponseCode;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.payload.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller để quản lý department
 * @author AnhNLT
 */
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class DepartmentController {
    DepartmentService departmentService;

    /**
     * Api lấy danh sách department
     * @return
     *      Trường hợp lấy được department
     *          code: 200
     *          departments: danh sách department
     *      Trường hợp không lấy được department
     *          code: 500
     *          message: {
     *              code: "ER023"
     *              params: []
     *          }
     */
    @GetMapping
    public ApiResponse<List<DepartmentDTO>> getListDepartments() {
        try {
            return ApiResponse.<List<DepartmentDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .departments(departmentService.getDepartments())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<DepartmentDTO>>builder()
                    .code(ResponseCode.ERROR.getCode())
                    .message(ApiResponse.<ErrorMessage>builder()
                            .code(ErrorMessage.DEPARTMENT_ERROR.getCode())
                            .params(ErrorMessage.DEPARTMENT_ERROR.getParams())
                            .build())
                    .build();
        }

    }
}
