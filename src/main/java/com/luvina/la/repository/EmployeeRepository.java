package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);
    Optional<List<Employee>> findByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName, Sort sort);
    Optional<List<Employee>> findByEmployeeNameLike(String employeeName, Sort sort);
    Long countByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName);
    Long countByEmployeeNameLike(String employeeName);
}
