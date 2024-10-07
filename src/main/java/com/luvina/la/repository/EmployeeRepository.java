/**
 * Copyright(C) 2024  Luvina
 * EmployeeRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);
    Optional<List<Employee>> findByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName, Pageable pageable);
    Optional<List<Employee>> findByEmployeeNameLike(String employeeName, Pageable pageable);
    Long countByDepartment_departmentIdAndEmployeeNameLike(Long departmentId, String employeeName);
    Long countByEmployeeNameLike(String employeeName);

    @Query(value = "SELECT new com.luvina.la.dto.EmployeeDTO(" +
            "e.employeeId, " +
            "e.employeeName, " +
            "e.employeeBirthDate, " +
            "d.departmentName, " +
            "e.employeeEmail, " +
            "e.employeeTelephone, " +
            "c.certificationName, " +
            "ec.endDate, " +
            "ec.score) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.employeeCertificationList ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "AND (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%'))")
    List<EmployeeDTO> findEmployees(@Param("employeeName") String employeeName,
                                    @Param("departmentId") Long departmentId,
                                    Pageable pageable);
}
