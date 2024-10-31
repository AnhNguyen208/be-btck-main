/**
 * Copyright(C) 2024  Luvina
 * CertificationRepository.java, 10/10/2024 AnhNLT
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Các thao tác liên quan đến thực thể Certification
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
