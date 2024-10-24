/**
 * Copyright(C) 2024  Luvina
 * EditEmployeeRequest.java, 21/10/2024 AnhNLT
 */
package com.luvina.la.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 *  Chứa thông tin nhân viên được truyền từ phía client
 *  khi cập nhật thông tin một nhân viên
 *  @author AnhNLT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EditEmployeeRequest {
    Long employeeId;
    String employeeName;
    String employeeBirthDate;
    String employeeEmail;
    String employeeTelephone;
    String employeeNameKana;
    String employeeLoginId;
    String employeeLoginPassword;
    Long departmentId;
    List<CertificationRequest> certifications;
}
