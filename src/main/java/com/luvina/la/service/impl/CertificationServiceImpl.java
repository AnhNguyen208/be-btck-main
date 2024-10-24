/**
 * Copyright(C) 2024  Luvina
 * CertificationServiceImpl.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
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
 * Lấy thông tin Certification từ CSDL
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
     * Lấy danh sách tất cả Certification
     * @return Danh sách đối tượng CertificationDTO
     */
    @Override
    public List<CertificationDTO> getCertifications() {
        return (List<CertificationDTO>) certificationMapper.toList(certificationRepository.findAll());
    }

    /**
     * Kiểm tra sự tồn tại của certification bằng id
     * @param certificationId id muốn kiểm tra
     * @return
     *      true: Tồn tại
     *      false: Không tồn tại
     */
    @Override
    public boolean checkExistsById(Long certificationId) {
        return certificationRepository.existsById(certificationId);
    }
}
