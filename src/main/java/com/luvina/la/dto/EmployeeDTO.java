/**
 * Copyright(C) 2024  Luvina
 * EmployeeDTO.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Thông tin Employee hiển thị cho client trong ADM002
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDTO implements Serializable {

    static final long serialVersionUID = 6868189362900231672L;

    Long employeeId;
    String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date employeeBirthDate;

    String departmentName;
    String employeeEmail;
    String employeeTelephone;
    String certificationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date endDate;

    BigDecimal score;

    public EmployeeDTO(Long employeeId, String employeeName,
                       Date employeeBirthDate, String departmentName,
                       String employeeEmail, String employeeTelephone,
                       String certificationName, Date endDate,
                       BigDecimal score) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeBirthDate = employeeBirthDate;
        this.departmentName = departmentName;
        this.employeeEmail = employeeEmail;
        this.employeeTelephone = employeeTelephone;
        this.certificationName = certificationName;
        this.endDate = endDate;
        this.score = score;
    }
}
