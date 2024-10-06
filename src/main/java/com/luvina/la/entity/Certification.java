/**
 * Copyright(C) 2024  Luvina
 * Certification.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Certification implements Serializable {
    private static final long serialVersionUID = 1656361441429623538L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id", unique = true)
    Long certificationId;

    @Column(name = "certification_name", length = 50, nullable = false)
    String certificationName;

    @Column(name = "certification_level", nullable = false)
    Integer certificationLevel;

    @OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    List<EmployeeCertification> employeeCertificationList;

}
