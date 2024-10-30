/**
 * Copyright(C) 2024  Luvina
 * DepartmentController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.constant.ErrorConstants;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.payload.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller xử lý các yêu cầu HTTP liên quan đến department
 * Bao gồm chức năng lấy danh sách department
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
     * Lấy danh sách department từ DepartmentService
     * @return
     *      Trường hợp lấy được department
     *      {
     *          "code": "200"
     *          "departments": [
     *              {
     *                  "departmentId": "1",
     *                  "departmentName": "Phòng DEV1",
     *              },
     *              {
     *                  "departmentId": "2",
     *                  "departmentName": "Phòng DEV2",
     *              }
     *          ]
     *      }
     *      Trường hợp không lấy được department
     *      {
     *          "code": "500"
     *          "message":  {
     * 	            "code": "ER023"
     * 	            "params": []
     *          }
     *      }
     */
    @GetMapping
    public ResponseEntity<?> getListDepartments() {
        try {
            // Lấy danh sách department từ service
            List<DepartmentDTO> result = departmentService.getDepartments();

            // Tạo response
            ApiResponse<List<DepartmentDTO>> response = ApiResponse.<List<DepartmentDTO>>builder()
                    .departments(result)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorConstants.ER023);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
