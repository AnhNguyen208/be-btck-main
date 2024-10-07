/**
 * Copyright(C) 2024  Luvina
 * EmployeeServiceImpl.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service.impl;

import com.luvina.la.entity.Employee;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Lấy thông tin Employee từ CSDL
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;

    /**
     * Lấy danh sách tất cả Employee
     * @return Danh sách đối tượng EmployeeDTO
     */
    @Override
    public List<EmployeeDTO> getEmployees(String employeeName, String departmentId, String ordEmployeeName, String ordCertificationName, String ordEndDate, String offset, String limit) {
        Pageable pageable = PageRequest.of(Integer.parseInt(offset) / Integer.parseInt(limit), Integer.parseInt(limit));
        return employeeRepository.findEmployees(employeeName,
                isLong(departmentId) ? Long.parseLong(departmentId) : null,
                ordEmployeeName,
                ordCertificationName,
                ordEndDate,
                pageable);
    }

    /**
     * Kiểm tra departmentId có phải số không
     * @param str chuỗi departmentId
     * @return Nếu là số thì trả về true, còn không trả về false
     */
    private boolean isLong(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Số lượng Employee
     * @return Số lượng Employee
     */
    @Override
    public Long countEmployees(String employeeName, String departmentId) {
        return employeeRepository.countEmployees(employeeName,
                isLong(departmentId) ? Long.parseLong(departmentId) : null);
    }
}
