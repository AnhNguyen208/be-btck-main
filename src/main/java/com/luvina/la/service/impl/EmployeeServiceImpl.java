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
    public List<EmployeeDTO> getEmployees(String employee_name, String department_id, String ord_employee_name, String ord_certification_name, String ord_end_date, String offset, String limit) {
        Pageable pageable = PageRequest.of(Integer.parseInt(offset) / Integer.parseInt(limit), Integer.parseInt(limit));
        Sort sort = Sort.by(
                new Sort.Order(Sort.Direction.fromString(ord_employee_name), "employeeName"),
                new Sort.Order(Sort.Direction.fromString(ord_certification_name), "employeeCertificationList.certification.certificationName"),
                new Sort.Order(Sort.Direction.fromString(ord_end_date), "employeeCertificationList.endDate")
        );
        if(isLong(department_id)) {
            Optional<List<Employee>> list = employeeRepository.findByDepartment_departmentIdAndEmployeeNameLike(Long.parseLong(department_id), "%" + employee_name + "%", sort);
            return list.map(employees -> (List<EmployeeDTO>) employeeMapper.toList(employees)).orElse(null);
        } else {
            Optional<List<Employee>> list = employeeRepository.findByEmployeeNameLike("%" + employee_name + "%", sort);
            return list.map(employees -> (List<EmployeeDTO>) employeeMapper.toList(employees)).orElse(null);
        }
    }
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
    @Override
    public Long countEmployees(String employee_name, String department_id) {
        if(isLong(department_id)) {
            return employeeRepository.countByDepartment_departmentIdAndEmployeeNameLike(Long.parseLong(department_id), "%" + employee_name + "%");
        } else {
            return employeeRepository.countByEmployeeNameLike("%" + employee_name + "%");
        }
    }
}
