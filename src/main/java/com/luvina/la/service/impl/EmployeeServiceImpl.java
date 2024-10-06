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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    @Override
    public List<EmployeeDTO> getEmployees(String employeeName, String departmentId, String ordEmployeeName, String ordCertificationName, String ordEndDate, String offset, String limit) {
        Sort sort = Sort.by(
                new Sort.Order(Sort.Direction.fromString(ordEmployeeName), "employeeName"),
                new Sort.Order(Sort.Direction.fromString(ordCertificationName), "employeeCertificationList.certification.certificationName"),
                new Sort.Order(Sort.Direction.fromString(ordEndDate), "employeeCertificationList.endDate")
        );
        Pageable pageable = PageRequest.of(Integer.parseInt(offset) / Integer.parseInt(limit), Integer.parseInt(limit), sort);

        Optional<List<Employee>> list = employeeRepository.findByDepartment_departmentIdAndEmployeeNameLike(Long.parseLong(departmentId), "%" + employeeName + "%", pageable);
        return list.map(employees -> (List<EmployeeDTO>) employeeMapper.toList(employees)).orElse(null);
    }

    @Override
    public Long countEmployees(String employeeName, String departmentId) {
        return employeeRepository.countByDepartment_departmentIdAndEmployeeNameLike(Long.parseLong(departmentId), "%" + employeeName + "%");
    }
}
