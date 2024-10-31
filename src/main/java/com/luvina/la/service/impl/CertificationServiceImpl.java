/**
 * Copyright(C) 2024  Luvina
 * CertificationServiceImpl.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.CertificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service triển khai các thao tác với dữ liệu Certification từ CSDL.
 * Được sử dụng để quản lý và xử lý các thông tin liên quan đến Certification.
 *
 * @author AnhNLT
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CertificationServiceImpl implements CertificationService {
    CertificationRepository certificationRepository;
    CertificationMapper certificationMapper;

    /**
     * Lấy danh sách tất cả các chứng chỉ (Certification) dưới dạng DTO.
     *
     * @return Danh sách các đối tượng CertificationDTO
     */
    @Override
    public List<CertificationDTO> getCertifications() {
        return (List<CertificationDTO>) certificationMapper.toList(certificationRepository.findAll());
    }

    /**
     * Kiểm tra sự tồn tại của một chứng chỉ dựa trên ID.
     *
     * @param certificationId ID của chứng chỉ cần kiểm tra
     * @return true nếu chứng chỉ tồn tại, ngược lại false
     */
    @Override
    public boolean checkExistsById(Long certificationId) {
        return certificationRepository.existsById(certificationId);
    }
}
