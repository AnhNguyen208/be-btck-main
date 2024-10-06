/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationRepository.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.entity.EmployeeCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCertificationRepository extends JpaRepository<EmployeeCertification, Long> {
}
