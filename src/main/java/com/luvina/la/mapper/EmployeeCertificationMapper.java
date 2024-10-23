/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationMapper.java, 18/10/2024 AnhNLT
 */
package com.luvina.la.mapper;

import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.request.CertificationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Date;

/**
 * Chuyển đổi giữa CertificationRequest và EmployeeCertification
 */
@Mapper
public interface EmployeeCertificationMapper {
//    @Mapping(source = "request.certificationId", target = "certification", qualifiedByName = "setCertification")
    @Mapping(source = "certificationStartDate", target = "startDate", qualifiedByName = "setStartDate")
    @Mapping(source = "certificationEndDate", target = "endDate", qualifiedByName = "setEndDate")
    @Mapping(source = "employeeCertificationScore", target = "score")
    EmployeeCertification toEntity(CertificationRequest request);

    Iterable<EmployeeCertification> toList(Iterable<CertificationRequest> iterable);

    @Named("setStartDate")
    static Date setStartDate(String startDate) {
        return new Date(startDate);
    }

    @Named("setEndDate")
    static Date setEndDate(String endDate) {
        return new Date(endDate);
    }

}
