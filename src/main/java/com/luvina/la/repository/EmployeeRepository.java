/**
 * Copyright(C) 2024  Luvina
 * EmployeeRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);
    Optional<List<Employee>> findByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName, Pageable pageable);
    Optional<List<Employee>> findByEmployeeNameLike(String employeeName, Pageable pageable);
    Long countByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName);
    Long countByEmployeeNameLike(String employeeName);
}
