/**
 * Copyright(C) 2024  Luvina
 * DepartmentServiceImpl.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service.impl;

import com.luvina.la.service.DepartmentService;
import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.repository.DepartmentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lấy thông tin Department từ CSDL
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository departmentRepository;
    DepartmentMapper departmentMapper;

    /**
     * Lấy danh sách tất cả Department
     * @return Danh sách đối tượng DepartmentDTO
     */
    @Override
    public List<DepartmentDTO> getDepartments() {
        return (List<DepartmentDTO>) departmentMapper.toList(departmentRepository.findAll());
    }

    @Override
    public boolean checkExistsById(Long departmentId) {
        return departmentRepository.existsById(departmentId);
    }
}
