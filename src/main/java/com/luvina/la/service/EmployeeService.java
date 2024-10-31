/**
 * Copyright(C) 2024  Luvina
 * EmployeeService.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service;


import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.request.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getEmployees(String employeeName, String departmentId, String ordEmployeeName, String ordCertificationName, String ordEndDate, String offset, String limit);

    Long countEmployees(String employeeName, String departmentId);

    Long addEmployee(EmployeeRequest request);

    Long editEmployee(EmployeeRequest request);

    boolean checkExistsByEmployeeLoginIAdd(String employeeLoginId);

    boolean checkExistsByEmployeeLoginIEdit(Long employeeId, String employeeLoginId);

    EmployeeDetailDTO getDetailEmployee(Long employeeId);

    void deleteEmployee(Long employeeId);

    boolean checkExistsById(Long employeeId);
}
