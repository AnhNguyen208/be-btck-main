package com.luvina.la.service;


import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getEmployees(String employee_name, String department_id, String ord_employee_name, String ord_certification_name, String ord_end_date, String offset, String limit);

    Long countEmployees(String employee_name, String department_id);
}
