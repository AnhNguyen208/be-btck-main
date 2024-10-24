/**
 * Copyright(C) 2024  Luvina
 * Employee.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Thông tin Employee trong CSDL
 * @author AnhNLT
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee implements Serializable {
    private static final long serialVersionUID = 1754309112469045276L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true)
    Long employeeId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false, referencedColumnName = "department_id")
    @JsonBackReference
    Department department;

    @Column(name = "employee_name", nullable = false)
    String employeeName;

    @Column(name = "employee_name_kana")
    String employeeNameKana;

    @Column(name = "employee_birth_date")
    Date employeeBirthDate;

    @Column(name = "employee_email", nullable = false)
    String employeeEmail;

    @Column(name = "employee_telephone", length = 50)
    String employeeTelephone;

    @Column(name = "employee_login_id", length = 50, nullable = false)
    String employeeLoginId;

    @Column(name = "employee_login_password", length = 100)
    String employeeLoginPassword;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    List<EmployeeCertification> certifications;
}
