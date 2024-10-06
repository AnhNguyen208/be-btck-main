/**
 * Copyright(C) 2024  Luvina
 * CertificationDTO.java, 04/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CertificationDTO {
    String certificationName;
    Date endDate;
    BigDecimal score;
}
