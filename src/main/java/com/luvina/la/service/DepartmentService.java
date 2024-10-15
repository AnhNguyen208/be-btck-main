/**
 * Copyright(C) 2024  Luvina
 * DepartmentService.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service;

import com.luvina.la.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getDepartments();

    boolean checkExistsById(Long departmentId);
}
