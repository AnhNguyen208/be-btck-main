/**
 * Copyright(C) 2024  Luvina
 * EmployeeDTO.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO implements Serializable {

    static final long serialVersionUID = 6868189362900231672L;

    Long employeeId;
    String employeeName;
    Date employeeBirthDate;
    String departmentName;
    String employeeEmail;
    String employeeTelephone;
    String certificationName;
    Date endDate;
    BigDecimal score;
}
