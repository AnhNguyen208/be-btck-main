package com.luvina.la.controller;

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
 *
 */
@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class DepartmentController {
    DepartmentService departmentService;

    /**
     * Api lấy danh sách phòng ban
     * @return
     * Trường hợp lấy được department
     * code: 200
     * departments: danh sách department
     * Trường hợp không lấy được department
     * code: 500
     * message: {
     *     code: "ER023"
     *     params: []
     * }
     */
    @GetMapping
    public ApiResponse<List<DepartmentDTO>> getListDepartments() {
        return ApiResponse.<List<DepartmentDTO>>builder()
                .departments(departmentService.getDepartments())
                .build();
    }
}
