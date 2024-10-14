/**
 * Copyright(C) 2024  Luvina
 * CertificationController.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.controller;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.payload.ErrorMessage;
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
 * Controller để quản lý Certification
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
     * Api lấy danh sách certification
     * @return
     *      Trường hợp lấy được certification
     *          code: 200
     *          certifications: danh sách certification
     *      Trường hợp không lấy được certification
     *          code: 500
     *          message: {
     *              code: "ER023"
     *              params: []
     *          }
     */
    @GetMapping
    public ResponseEntity<?> getListCertifications() {
        try {
            List<CertificationDTO> result = certificationService.getCertifications();
            ApiResponse<List<CertificationDTO>> response = ApiResponse.<List<CertificationDTO>>builder()
                    .certifications(result)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ApiResponse<?> response = ApiResponse.createMessageResponse(ErrorMessage.ER023);

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
