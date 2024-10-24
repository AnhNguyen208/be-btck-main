/**
 * Copyright(C) 2024  Luvina
 * EmployeeDetailDTO.java, 21/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Thông tin Employee hiển thị cho client trong ADM003
 * @author AnhNLT
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeDetailDTO implements Serializable {
    static final long serialVersionUID = 7992402038631018009L;

    Long employeeId;
    String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date employeeBirthDate;

    Long departmentId;
    String departmentName;
    String employeeEmail;
    String employeeTelephone;
    String employeeNameKana;
    String employeeLoginId;
    List<EmployeeCertificationDTO> certifications;
}
