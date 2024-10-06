/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertification.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees_certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCertification implements Serializable {
    private static final long serialVersionUID = 4027475275702578185L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id")
    Long employeeCertificationId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false, referencedColumnName = "employee_id")
    @JsonBackReference
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "certification_id", nullable = false, referencedColumnName = "certification_id")
    @JsonBackReference
    Certification certification;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "score", precision = 5, scale = 2)
    BigDecimal score;
}
