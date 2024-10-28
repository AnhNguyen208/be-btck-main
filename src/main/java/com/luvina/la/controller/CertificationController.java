/**
 * Copyright(C) 2024  Luvina
 * CertificationController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.payload.response.ApiResponse;
import com.luvina.la.service.CertificationService;
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
 * Controller xử lý các yêu cầu HTTP liên quan đến certification
 * Bao gồm chức năng lấy danh sách certification
 * @author AnhNLT
 */
@RestController
@RequestMapping("/certifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin
public class CertificationController {
    CertificationService certificationService;

    /**
     * Lấy danh sách certification từ CertificationService
     * @return
     *      Trường hợp lấy được certification
     *      {
     *          "code": "200"
     *          "certifications": [
     *              {
     *                  "certificationId": "1",
     *                  "certificationName": "Trình độ tiếng Nhật cấp 1",
     *              },
     *              {
     *                  "certificationId": "2",
     *                  "certificationName": "Trình độ tiếng Nhật cấp 2",
     *              }
     *          ]
     *      }
     *      Trường hợp không lấy được certification
     *      {
     *          "code": 500
     *          "message": {
     *              "code": "ER023"
     *              "params"": []
     *          }
     *      }
     */
    @GetMapping
    public ResponseEntity<?> getListCertifications() {
        // Lấy danh sách certification từ service
        List<CertificationDTO> result = certificationService.getCertifications();

        // Tạo response
        ApiResponse<List<CertificationDTO>> response = ApiResponse.<List<CertificationDTO>>builder()
                .certifications(result)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
