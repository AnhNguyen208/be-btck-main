/**
 * Copyright(C) 2024  Luvina
 * EmployeeService.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.service;


import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.request.AddEmployeeRequest;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getEmployees(String employeeName, String departmentId, String ordEmployeeName, String ordCertificationName, String ordEndDate, String offset, String limit);

    Long countEmployees(String employeeName, String departmentId);

    Long addEmployee(AddEmployeeRequest request);

    boolean checkExistsByEmployeeLoginId(String employeeLoginId);

    EmployeeDetailDTO getDetailEmployee(Long employeeId);
}
