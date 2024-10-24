/**
 * Copyright(C) 2024  Luvina
 * CertificationRequest.java, 16/10/2024 AnhNLT
 */
package com.luvina.la.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

/**
 *  Chứa thông tin về trình độ tiếng Nhật được truyền từ phía
 *  client khi tạo mới một nhân viên
 *  @author AnhNLT
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificationRequest {
    Long certificationId;
    String certificationStartDate;
    String certificationEndDate;
    BigDecimal employeeCertificationScore;
}
