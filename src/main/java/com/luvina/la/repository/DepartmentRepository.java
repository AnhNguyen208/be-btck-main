/**
 * Copyright(C) 2024  Luvina
 * DepartmentRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * các thao tác liên quan đến thực thể Department
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
