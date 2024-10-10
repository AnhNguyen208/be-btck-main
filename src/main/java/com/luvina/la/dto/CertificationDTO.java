/**
 * Copyright(C) 2024  Luvina
 * CertificationDTO.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Thông tin Certification hiển thị cho client
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificationDTO {
    Long certificationId;
    String certificationName;
}
