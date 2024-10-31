/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.entity.EmployeeCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Các thao tác liên quan đến thực thể EmployeeCertification
 */
@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification, Long> {
    /**
     * Xóa tất cả các bản ghi EmployeeCertification có liên kết với employeeId được chỉ định.
     *
     * @param employeeId ID của nhân viên mà các chứng chỉ sẽ bị xóa
     */
    @Modifying
    @Query("DELETE FROM EmployeeCertification ec WHERE ec.employee.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
