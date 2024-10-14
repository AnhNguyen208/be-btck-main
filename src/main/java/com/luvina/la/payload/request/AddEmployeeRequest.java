package com.luvina.la.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddEmployeeRequest {
    String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date employeeBirthDate;

    String employeeEmail;
    String employeeTelephone;
    String employeeNameKana;
    String employeeLoginId;
    String employeeLoginPassword;
    Long departmentId;
    List<CertificationRequest> certifications;
}
