/**
 * Copyright(C) 2024  Luvina
 * EmployeeServiceImpl.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.mapper.EmployeeCertificationMapper;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.request.AddEmployeeRequest;
import com.luvina.la.payload.request.CertificationRequest;
import com.luvina.la.payload.request.EditEmployeeRequest;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.repository.EmployeeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Lấy thông tin Employee từ CSDL
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    EmployeeMapper employeeMapper;
    EmployeeCertificationMapper employeeCertificationMapper;
    CertificationRepository certificationRepository;
    EmployeeCertificationRepository employeeCertificationRepository;

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

    /**
     * Lưu thông tin employee vào CSDL
     * @param request Thông tin employee nhận từ controller
     * @return ID employee mới
     */
    @Transactional
    @Override
    public Long addEmployee(AddEmployeeRequest request) {
        Employee employee = employeeMapper.toEntityAdd(request);

        String encodePassword = new BCryptPasswordEncoder().encode(employee.getEmployeeLoginPassword());
        employee.setEmployeeLoginPassword(encodePassword);
        employee.setDepartment(departmentRepository.findById(request.getDepartmentId()).orElseThrow());

        employee = employeeRepository.save(employee);

        for (CertificationRequest request1: request.getCertifications()) {
            EmployeeCertification ec = employeeCertificationMapper.toEntity(request1);
            ec.setCertification(certificationRepository.findById(request1.getCertificationId()).orElseThrow());
            ec.setEmployee(employee);

            employeeCertificationRepository.save(ec);
        }

        return employee.getEmployeeId();
    }

    /**
     * Lưu thông tin employee vào CSDL
     * @param request Thông tin employee nhận từ controller
     * @return ID employee mới
     */
    @Transactional
    @Override
    public Long editEmployee(EditEmployeeRequest request) {
        Employee employee = employeeMapper.toEntityEdit(request);
        Employee oldEmployee = employeeRepository.findById(employee.getEmployeeId()).orElseThrow();

        if ("".compareTo(employee.getEmployeeLoginPassword()) == 0) {
            employee.setEmployeeLoginPassword(oldEmployee.getEmployeeLoginPassword());
        } else {
            String encodePassword = new BCryptPasswordEncoder().encode(employee.getEmployeeLoginPassword());
            employee.setEmployeeLoginPassword(encodePassword);
        }

        employee.setDepartment(departmentRepository.findById(request.getDepartmentId()).orElseThrow());
        employee.setCertifications(oldEmployee.getCertifications());

        employee = employeeRepository.save(employee);

        employeeCertificationRepository.deleteAll(employee.getCertifications());

        List<CertificationRequest> newEcList = request.getCertifications();

        for (CertificationRequest newEc : newEcList) {
            EmployeeCertification ec = employeeCertificationMapper.toEntity(newEc);
            ec.setCertification(certificationRepository.findById(newEc.getCertificationId()).orElseThrow());
            ec.setEmployee(employee);

            employeeCertificationRepository.save(ec);
        }

        return employee.getEmployeeId();
    }

    /**
     * Kiểm tra employeeLoginId đã tồn tại chưa
     * @param employeeLoginId employeeLoginId cần kiểm tra
     * @return
     *      true: Đã tồn tại trong CSDL
     *      false: Chưa tồn tại trong CSDL
     */
    @Override
    public boolean checkExistsByEmployeeLoginIAdd(String employeeLoginId) {
        return employeeRepository.existsByEmployeeLoginId(employeeLoginId);
    }

    /**
     * Kiểm tra employeeLoginId có trùng với employeeLoginId khác không
     * @param employeeId employeeId cần kiểm tra
     * @param employeeLoginId employeeLoginId cần kiểm tra
     * @return
     *      true: Trùng với employeeLoginId khác trong CSDL
     *      false: Không trùng với employeeLoginId khác trong CSDL
     */
    @Override
    public boolean checkExistsByEmployeeLoginIEdit(Long employeeId, String employeeLoginId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        System.out.println("check: " + employeeRepository.existsByEmployeeLoginId(employeeLoginId));

        if (employeeRepository.existsByEmployeeLoginId(employeeLoginId)) {
            System.out.println("Check: " + employee.getEmployeeLoginId().equals(employeeLoginId));
            return !employee.getEmployeeLoginId().equals(employeeLoginId);
        }

        return false;
    }


    /**
     * Lấy thông tin employee theo employeeId từ CSDL
     * @param employeeId employeeId
     * @return thông tin employee từ CSDL
     */
    @Override
    @Transactional
    public EmployeeDetailDTO getDetailEmployee(Long employeeId) {
        return employeeMapper.toDetailDto(employeeRepository.findById(employeeId).orElseThrow());
    }

    @Override
    @Transactional
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow();
        employeeCertificationRepository.deleteAll(employee.getCertifications());

        employeeRepository.delete(employee);
    }

    @Override
    public boolean checkExistsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
