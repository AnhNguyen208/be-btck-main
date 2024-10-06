/**
 * Copyright(C) 2024  Luvina
 * Department.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Department implements Serializable {
    private static final long serialVersionUID = 6112594650489952415L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true)
    Long departmentId;

    @Column(name = "department_name", length = 50, nullable = false)
    String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    List<Employee> employeeList;
}
