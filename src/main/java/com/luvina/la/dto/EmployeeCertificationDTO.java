/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationDTO.java, 21/10/2024 AnhNLT
 */
package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Thông tin Employee hiển thị cho client trong ADM003
 * @author AnhNLT
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCertificationDTO implements Serializable{
    static final long serialVersionUID = 8103180130956264295L;

    Long certificationId;
    String certificationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    Date endDate;
    BigDecimal score;
}
